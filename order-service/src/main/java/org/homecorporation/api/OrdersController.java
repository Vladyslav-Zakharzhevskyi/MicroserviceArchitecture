package org.homecorporation.api;

import org.homecorporation.dto.OrderCreatedDto;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrderCreatedDto> order(@RequestParam UUID productId, @RequestParam Integer count) {

        OrderCreatedDto order = ordersService.order(productId, count);

        return ResponseEntity.ok(order);
    }

}
