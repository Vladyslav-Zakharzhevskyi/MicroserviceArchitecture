package org.homecorporation.api;


import org.homecorporation.dto.ProductInfoDto;
import org.homecorporation.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductInfoDto getProductById(@PathVariable("id") UUID id) {
        return productsService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<ProductInfoDto> getProductsByIds(@RequestParam(name = "onlyAvailable") Boolean onlyAvailable,
                                                 @RequestBody List<UUID> ids) {
        return productsService.getProducts(ids, onlyAvailable);
    }

}
