package org.homecorporation.api.req;

public record IsEnoughForOrderAvailabilityRequest(String ref, Integer orderedCount) {}
