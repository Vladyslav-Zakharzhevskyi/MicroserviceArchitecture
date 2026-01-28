package org.homecorporation.api;


import org.homecorporation.dto.ProductDTO;
import org.homecorporation.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping(method = RequestMethod.GET)
    public String getProducts(@RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                                               Model model) {
        List<ProductDTO> products = productsService.getProducts(onlyAvailable, Boolean.TRUE);
        model.addAttribute("products", products);
        return "view/products";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{productId}")
    private ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "productId") UUID id,
                                                  @RequestParam(name = "skipAvailability", required = false, defaultValue = "false") Boolean skipAvailability) {

        return ResponseEntity
                .ok(productsService.getProduct(id, !skipAvailability));
    }



}
