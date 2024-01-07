package com.advanced.app.v1;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepositoryV1;
    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {

        TraceStatus traceStatus = null;

        try {
            traceStatus = trace.begin("OrderServiceV1.orderItem()");
            orderRepositoryV1.saveItem(itemId);
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }

    }
}
