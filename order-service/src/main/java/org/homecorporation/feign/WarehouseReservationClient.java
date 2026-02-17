package org.homecorporation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "warehouse-service", url = "${eco.url.warehouse}" + "/reservation")
public interface WarehouseReservationClient {
    @RequestMapping(path = "/reserve/{productRef}/{count}", method = RequestMethod.POST)
    Integer reserve(@PathVariable(name = "productRef") String ref, @PathVariable Integer count);

    @RequestMapping(path = "/cancel/{productRef}/{count}", method = RequestMethod.POST)
    ResponseEntity<Boolean> cancelReservation(@PathVariable(name = "productRef") String ref, @PathVariable Integer count);

}
