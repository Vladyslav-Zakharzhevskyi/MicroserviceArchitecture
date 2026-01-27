package org.homecorporation.api;

import org.homecorporation.api.req.IsEnoughForOrderAvailabilityRequest;
import org.homecorporation.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/warehouse/availability")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(path = "/{ref}", method = RequestMethod.GET)
    public Integer getAvailability(@PathVariable("ref") String ref) {
        return warehouseService.getProductAvailability(ref);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Integer> getAvailability(@RequestBody List<String> refs) {
        return warehouseService.getProductsAvailability(refs);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/isEnoughForOrder")
    public ResponseEntity<Boolean> isEnoughForOrder(@RequestBody IsEnoughForOrderAvailabilityRequest request) {
        return ResponseEntity
                .ok(warehouseService.getProductAvailability(
                        request.ref()) >= request.orderedCount());
    }

}
