package org.homecorporation.feign.exception;

public class WarehouseServiceInteractionException extends RuntimeException {

    public WarehouseServiceInteractionException(Throwable throwable) {
        super(String.format("WarehouseService is not reachable. Message: %s", throwable.getMessage()));
    }
}
