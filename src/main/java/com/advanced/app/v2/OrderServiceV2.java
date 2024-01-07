package com.advanced.app.v2;

import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.hellotrace.HelloTraceV1;
import com.advanced.app.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;
    private final HelloTraceV2 trace;

    public void orderItem(String itemId, TraceId traceId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.beginSync(traceId, "OrderServiceV2.orderItem()");
            orderRepositoryV2.saveItem(itemId, traceStatus.getTraceId());
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }

    }
}
