package org.homecorporation.feign;

import io.github.resilience4j.retry.annotation.Retry;
import org.homecorporation.feign.fallback.WarehouseClientFallbackFactory;
import org.homecorporation.feign.request.IsEnoughForOrderAvailabilityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "warehouse-service", url = "${eco.url.warehouse}" + WarehouseClient.AVAILABILITY_SFX,
        fallbackFactory = WarehouseClientFallbackFactory.class)
@Retry(name = "warehouse-service")
public interface WarehouseClient {
    @GetMapping("/{ref}")
    Integer getAvailability(@PathVariable(name = "ref") String ref);
    @PostMapping
    Map<String, Integer> getAvailabilities(@RequestBody List<String> refs);
    @PostMapping("/isEnoughForOrder")
    Boolean isEnoughForOrder(@RequestBody IsEnoughForOrderAvailabilityRequest model);

    //todo are you sure that warehouse service is responsible only for availability ?
    String AVAILABILITY_SFX = "/inventory";
}
