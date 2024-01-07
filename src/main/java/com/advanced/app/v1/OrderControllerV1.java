package com.advanced.app.v1;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderServiceV1;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String orderItem(String itemId) {

        TraceStatus traceStatus = null;

        try {
            traceStatus = trace.begin("OrderControllerV1.orderItem()");
            orderServiceV1.orderItem(itemId);
            trace.end(traceStatus);
            return "ok";
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }
}
