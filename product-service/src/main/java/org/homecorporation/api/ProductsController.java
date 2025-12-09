package org.homecorporation.api;


import org.homecorporation.dto.ProductInfoDto;
import org.homecorporation.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;


    @RequestMapping(method = RequestMethod.GET)
    public String getAllProducts(@RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                                               Model model) {
        List<ProductInfoDto> products = productsService.getProducts(onlyAvailable);
        model.addAttribute("products", products);
        return "view/products";
    }



}
