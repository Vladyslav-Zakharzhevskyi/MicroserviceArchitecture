package org.homecorporation.feign;

import org.homecorporation.feign.fallback.WarehouseClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(value = "warehouse-service",
        contextId = "warehouse-service",
        fallbackFactory = WarehouseClientFallbackFactory.class)
public interface WarehouseClient {

    @GetMapping("/{ref}")
    Integer getAvailability(@PathVariable(name = "ref") String ref);
    @PostMapping
    Map<String, Integer> getAvailabilities(@RequestBody List<String> refs);
}
