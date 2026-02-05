package org.homecorporation.service;

import io.micrometer.core.annotation.Timed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.homecorporation.dto.OrderCreatedResult;
import org.homecorporation.dto.PaymentLink;
import org.homecorporation.dto.ProductDTO;
import org.homecorporation.exception.CantReleaseReservationExceptionForOrder;
import org.homecorporation.exception.OutOfStockException;
import org.homecorporation.feign.ProductServiceClient;
import org.homecorporation.feign.WarehouseReservationClient;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.model.ReleaseReservationOutbox;
import org.homecorporation.repository.OrderItemRepository;
import org.homecorporation.repository.OrderRepository;
import org.homecorporation.repository.ReleaseReservationOutBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class OrderServiceImpl implements OrdersService {

    private static final Logger log = LogManager.getLogger(OrderServiceImpl.class);
    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private WarehouseReservationClient warehouseReservationClient;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ExecutorService forkJoinPool;
    @Autowired
    private ReleaseReservationOutBoxRepository releaseReservationOutBoxRepository;

    @Timed(
            value = "order.create",
            description = "Time spent creating order",
            percentiles = {0.5, 0.95, 0.99},
            histogram = true,
            extraTags = {"domain", "order", "operation", "create"}
    )
    @Transactional
    public OrderCreatedResult order(UUID productId, Integer count) {
        ProductDTO product = productServiceClient.getProductById(productId, true);

        Integer isReserved = warehouseReservationClient.reserve(product.warehouseRef(), count);
        if (isReserved < 1) {
            throw new OutOfStockException(product, count);
        }

        Order order = new Order();
        order.setCreatedAt(Instant.now().atZone(ZoneId.systemDefault()));
        order.setExpiredAt(Instant.now().plusSeconds(15 * 60).atZone(ZoneId.systemDefault()));
        order.setStatus(Order.Status.CREATED);
        orderRepository.save(order);


        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductId(productId);
        orderItem.setQuantity(count);
        orderItem.setWarehouseRef(product.warehouseRef());
        orderItem.setProductPrice(product.price());
        orderItem.setTotalPrice(product.price().multiply(new BigDecimal(count)));
        orderItemRepository.save(orderItem);


        return new OrderCreatedResult(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                new PaymentLink()
        );
    }

    @Transactional
    @Override
    public void makeOrderExpiredAndSetUpReleaseReservation(Order order) {

        List<ReleaseReservationOutbox> outBoxReleaseModels = order.getItems()
                .stream()
                .map(orderItem -> {
                    ReleaseReservationOutbox outbox = new ReleaseReservationOutbox();
                    outbox.setOrder(order);
                    outbox.setStatus(ReleaseReservationOutbox.Status.TO_EXECUTE);
                    outbox.setWarehouseRef(orderItem.getWarehouseRef());
                    outbox.setQuantity(orderItem.getQuantity());
                    return outbox;
                })
                .toList();
        releaseReservationOutBoxRepository.saveAll(outBoxReleaseModels);

        //mark order as expired
        order.setStatus(Order.Status.EXPIRED);
        orderRepository.save(order);
    }

    @Override
    public void release(String warehouseRef, Integer count) {
        try {
            ResponseEntity<Boolean> response = warehouseReservationClient.cancelReservation(warehouseRef, count);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info(String.format("Successfully released '%s' items for warehouseRef '%s'", warehouseRef, count));
            } else {
                throw new CantReleaseReservationExceptionForOrder(warehouseRef, count, response.toString());
            }
        } catch (RuntimeException e) {
            throw new CantReleaseReservationExceptionForOrder(warehouseRef, count, e);
        }
    }
}
