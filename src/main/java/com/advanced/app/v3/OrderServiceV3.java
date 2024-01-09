package com.advanced.app.v3;

import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.hellotrace.HelloTraceV2;
import com.advanced.app.trace.logtrace.FieldLogTrace;
import com.advanced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV3;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderServiceV3.orderItem()");
            orderRepositoryV3.saveItem(itemId);
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }

    }
}
