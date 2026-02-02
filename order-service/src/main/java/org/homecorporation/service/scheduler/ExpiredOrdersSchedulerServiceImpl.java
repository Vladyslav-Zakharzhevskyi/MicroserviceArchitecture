package org.homecorporation.service.scheduler;

import org.homecorporation.feign.WarehouseReservationClient;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.repository.OrderRepository;
import org.homecorporation.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.homecorporation.util.TimeUtil.ZONE_ID_KYIV;

@Component
public class ExpiredOrdersSchedulerServiceImpl implements ExpiredOrdersSchedulerService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WarehouseReservationClient warehouseReservationClient;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void releaseExpired() {

        List<Order> orders =
                orderRepository.findExpired(TimeUtil.getDateTimeAt(ZONE_ID_KYIV));
        //collect
        Map<UUID, ExpiredInfo> groupedProducts = orders
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

        if (!groupedProducts.isEmpty()) {
            //cancel reservation
            groupedProducts.values()
                    .forEach(this::cancelReserve);

            //status EXPIRED
            orders.forEach(order -> order.setStatus(Order.Status.EXPIRED));

            orderRepository.saveAll(orders);
        }

        //todo write expired orders metrics to MeterRegistry

    }

    private void cancelReserve(ExpiredInfo info) {
        warehouseReservationClient.cancelReservation(info.warehouseRef, info.count.intValue());
    }


    private record ExpiredInfo(String warehouseRef, Long count) {}















}
