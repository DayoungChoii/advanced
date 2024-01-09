package com.advanced.app.v3;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderServiceV3;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String orderItem(String itemId) {

        TraceStatus traceStatus = null;

        try {
            traceStatus = trace.begin("OrderControllerV3.orderItem()");
            orderServiceV3.orderItem(itemId);
            trace.end(traceStatus);
            return "ok";
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }
}
