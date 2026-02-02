package org.homecorporation.service;

import io.micrometer.core.annotation.Timed;
import org.homecorporation.dto.OrderCreatedResult;
import org.homecorporation.dto.PaymentLink;
import org.homecorporation.dto.ProductDTO;
import org.homecorporation.exception.OutOfStockException;
import org.homecorporation.feign.ProductServiceClient;
import org.homecorporation.feign.WarehouseReservationClient;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.repository.OrderItemRepository;
import org.homecorporation.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private WarehouseReservationClient warehouseReservationClient;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

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
}
