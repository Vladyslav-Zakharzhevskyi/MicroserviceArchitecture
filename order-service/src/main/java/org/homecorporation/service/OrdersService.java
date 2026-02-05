package org.homecorporation.service;

import org.homecorporation.dto.OrderCreatedResult;
import org.homecorporation.model.Order;
import org.homecorporation.model.OrderItem;
import org.homecorporation.service.scheduler.ExpiredOrdersSchedulerServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrdersService {
    OrderCreatedResult order(UUID productId, Integer count);
    void makeOrderExpiredAndSetUpReleaseReservation(Order order);

    void release(String warehouseRef, Integer count);
}
