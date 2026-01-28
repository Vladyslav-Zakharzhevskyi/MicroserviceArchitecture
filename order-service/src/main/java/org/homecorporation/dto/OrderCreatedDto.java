package org.homecorporation.dto;

import java.util.UUID;

public record OrderCreatedDto(UUID productId, Integer count, Boolean hasAvailableCount) {}
