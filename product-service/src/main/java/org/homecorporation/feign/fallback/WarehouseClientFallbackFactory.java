package org.homecorporation.feign.fallback;

import io.opentelemetry.api.trace.Span;
import org.homecorporation.feign.WarehouseClient;
import org.homecorporation.feign.exception.WarehouseServiceInteractionException;
import org.homecorporation.feign.request.IsEnoughForOrderAvailabilityRequest;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WarehouseClientFallbackFactory implements FallbackFactory<WarehouseClient> {

    @Override
    public WarehouseClient create(Throwable cause) {
        return new WarehouseClient() {
            public Integer getAvailability(String ref) {
                throw new WarehouseServiceInteractionException(cause);
            }
            public Map<String, Integer> getAvailabilities(List<String> refs) {
                throw new WarehouseServiceInteractionException(cause);
            }
            public Boolean isEnoughForOrder(IsEnoughForOrderAvailabilityRequest model) {
                System.out.println("TraceId: " + Span.current().getSpanContext().getTraceId());
                throw new WarehouseServiceInteractionException(cause);
            }
        };
    }
}
