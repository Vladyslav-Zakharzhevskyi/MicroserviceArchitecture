package org.homecorporation.api;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import org.homecorporation.dto.OrderCreatedResult;
import org.homecorporation.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Observed(name = "order-observation", lowCardinalityKeyValues = "order-service.order.API")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrderCreatedResult> order(@RequestParam UUID productId, @RequestParam Integer count) {

        OrderCreatedResult order = ordersService.order(productId, count);

        return ResponseEntity.ok(order);
    }

}
