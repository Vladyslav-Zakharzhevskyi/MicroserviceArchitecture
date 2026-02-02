package org.homecorporation.service;

import org.homecorporation.feign.WarehouseReservationClient;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExpiredServiceImpl implements ExpiredService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WarehouseReservationClient warehouseReservationClient;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void releaseExpired() {
        List<Order> orders =
                orderRepository.findExpired(Instant.now());
        //collect
        Map<UUID, ExpiredInfo> collect = orders
                .stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toMap(OrderItem::getProductId,
                        orderItem -> new ExpiredInfo(orderItem.getWarehouseRef(), orderItem.getQuantity().longValue()),
                        (first, second) -> {
                            if (!first.warehouseRef.equals(second.warehouseRef)) {
                                throw new IllegalStateException(
                                        "Product mapped to multiple warehouses: " + first.warehouseRef + " / " + second.warehouseRef
                                );
                            }
                            return new ExpiredInfo(first.warehouseRef, first.count + second.count);
                        }));

        //cancel reservation
        collect.values()
                .forEach(this::cancelReserve);

        //status EXPIRED
        orders.forEach(order -> order.setStatus(Order.Status.EXPIRED));

        orderRepository.saveAll(orders);
    }

    private void cancelReserve(ExpiredInfo info) {
        warehouseReservationClient.cancelReservation(info.warehouseRef, info.count.intValue());
    }


    private record ExpiredInfo(String warehouseRef, Long count) {}















}
