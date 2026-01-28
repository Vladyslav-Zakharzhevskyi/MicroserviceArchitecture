package org.homecorporation.feign;

import org.homecorporation.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "product-service", url = "${eco.services.urls.products}")
public interface ProductServiceClient {
    @RequestMapping(method = RequestMethod.GET, path = "/{productId}")
    ProductDTO getProductById(@PathVariable(name = "productId") UUID productId, @RequestParam(name = "skipAvailability") Boolean skipAvailability);
}
