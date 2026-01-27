package org.homecorporation.feign.request;

public record IsEnoughForOrderAvailabilityRequest(String ref, Integer orderedCount) {}
