package org.homecorporation.feign;

import io.github.resilience4j.retry.annotation.Retry;
import org.homecorporation.feign.fallback.WarehouseClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import static org.homecorporation.feign.WarehouseClient.WH_SERVICE_NAME;

@FeignClient(value = WH_SERVICE_NAME, contextId = WH_SERVICE_NAME,
        fallbackFactory = WarehouseClientFallbackFactory.class)
@Retry(name = WH_SERVICE_NAME)
public interface WarehouseClient {

    String WH_SERVICE_NAME = "warehouse-service";

    @GetMapping("/{ref}")
    Integer getAvailability(@PathVariable(name = "ref") String ref);
    @PostMapping
    Map<String, Integer> getAvailabilities(@RequestBody List<String> refs);
}
