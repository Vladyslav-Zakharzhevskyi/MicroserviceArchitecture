package org.homecorporation.exceptions;

public class ProductAbsentInWarehouse extends RuntimeException {
    private final static String MESSAGE = "Product with ref '%s' is absent in warehouse database.";
    public ProductAbsentInWarehouse(String ref) {
        super(String.format(MESSAGE, ref));
    }
}
