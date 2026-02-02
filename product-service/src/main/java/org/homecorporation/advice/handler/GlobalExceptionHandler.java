package org.homecorporation.advice.handler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        Observation current = ObservationRegistry.create().getCurrentObservation();;

        if (current != null) {
            current.error(ex);
        }

        String traceId = MDC.get("traceId");
        String spanId  = MDC.get("spanId");

        log.error("Unhandled exception. traceId={}, spanId={}", traceId, spanId, ex);

        return ResponseEntity.status(500).body(
                Map.of(
                        "error", ex.getClass().getName(),
                        "traceId", traceId,
                        "spanId", spanId
                )
        );
    }
}
