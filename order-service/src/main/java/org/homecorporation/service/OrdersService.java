package org.homecorporation.service;

import org.homecorporation.dto.OrderCreatedResult;

import java.util.UUID;

public interface OrdersService {
    OrderCreatedResult order(UUID productId, Integer count);
}
