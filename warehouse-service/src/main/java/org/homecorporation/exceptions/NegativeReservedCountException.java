package org.homecorporation.exceptions;

public class NegativeReservedCountException extends RuntimeException {
    public NegativeReservedCountException(String productRef, Integer reservationCount) {
        super(String.format("Cannot decrease reserved count to '%s' for productRef: '%s'", reservationCount, productRef));
    }
}
