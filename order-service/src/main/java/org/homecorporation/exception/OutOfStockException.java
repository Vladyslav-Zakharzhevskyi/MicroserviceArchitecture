package org.homecorporation.exception;

import org.homecorporation.dto.ProductDTO;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(ProductDTO product, Integer count) {
        super(String.format("Product: '%s' has no '%s' available items on Warehouse Inventory", product.name(), count));
    }

}
