package org.homecorporation.exception;

import org.homecorporation.dto.ProductDTO;

public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(ProductDTO product, Integer count) {
        super(String.format("Product: '%s' has no '%s' available items on Warehouse Inventory", product.name(), count));
    }

}
