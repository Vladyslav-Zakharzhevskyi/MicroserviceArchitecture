package org.homecorporation.exception;

public class CantReleaseReservationExceptionForOrder extends RuntimeException {

    public static final String MSG = "Can't decrease reservation for Product with warehouseRef: '%s' to count: '%s'. ErrorMsg: '%s'";
    private final String errorMessage;

    public CantReleaseReservationExceptionForOrder(String warehouseRef, Integer count, String errorMessage) {
        super(String.format(MSG, warehouseRef, count, errorMessage));
        this.errorMessage = errorMessage;
    }

    public CantReleaseReservationExceptionForOrder(String warehouseRef, Integer count, Throwable ex) {
        super(String.format(MSG, warehouseRef, count, ex.getMessage()), ex);
        this.errorMessage = ex.getMessage();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
