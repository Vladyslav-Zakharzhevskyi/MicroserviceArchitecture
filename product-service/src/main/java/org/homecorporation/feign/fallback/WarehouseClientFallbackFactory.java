package org.homecorporation.feign.fallback;

import io.micrometer.tracing.Tracer;
import org.homecorporation.feign.WarehouseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WarehouseClientFallbackFactory implements FallbackFactory<WarehouseClient> {

    @Override
    public WarehouseClient create(Throwable cause) {
        return new WarehouseClient() {

            @Override
            public Integer getAvailability(String ref) {
                throw new RuntimeException(String.format("Problem with Call to WarehouseService. Message: %s", cause.getMessage()));
            }

            @Override
            public Map<String, Integer> getAvailabilities(List<String> refs) {
                throw new RuntimeException(String.format("Problem with Call to WarehouseService. Message: %s", cause.getMessage()));
            }

        };
    }
}
