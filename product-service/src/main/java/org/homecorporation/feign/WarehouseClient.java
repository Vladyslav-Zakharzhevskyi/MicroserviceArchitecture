package org.homecorporation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "warehouse-service", url = "http://warehouse-service:2222/api/v1/warehouse/availability")
public interface WarehouseClient {

    @GetMapping("/{ref}")
    Integer getAvailability(@PathVariable(name = "ref") String ref);
    @PostMapping
    Map<String, Integer> getAvailabilities(@RequestBody List<String> refs);
}
