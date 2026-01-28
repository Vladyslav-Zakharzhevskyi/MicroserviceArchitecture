package org.homecorporation.service;

import org.homecorporation.dto.OrderCreatedDto;

import java.util.UUID;

public interface OrdersService {
    OrderCreatedDto order(UUID productId, Integer count);
}
