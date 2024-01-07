package com.advanced.app.v1;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    public void saveItem(String itemId) {
        TraceStatus traceStatus = null;

        try {
            traceStatus = trace.begin("OrderRepositoryV1.saveItem()");
            if (itemId.equals("ex")) {
                throw new IllegalArgumentException("예외 발생!");
            }

            sleep(1000);
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }

    private void sleep(int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
