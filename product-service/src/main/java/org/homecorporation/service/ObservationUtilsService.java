package org.homecorporation.service;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObservationUtilsService {
    @Autowired
    private static ObservationRegistry observationRegistry;
    @Autowired
    private static Tracer tracer;

    public static String getObservationInfo() {
        SpanContext spanContext = Span.current().getSpanContext();
        return String.format("TraceId: %s - SpanId: %s", spanContext.getTraceId(), spanContext.getSpanId());
    }

}
