package org.homecorporation.dto;

import org.homecorporation.model.Order;

import java.time.ZonedDateTime;
import java.util.UUID;

public record OrderCreatedResult(UUID orderId,
                                 Order.Status status,
                                 ZonedDateTime createdAt,
                                 PaymentLink paymentLink) {}
