package com.advanced.app.trace.logtrace;

import com.advanced.app.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {

    @Test
    public void begin_end() throws Exception{
        FieldLogTrace logTrace = new FieldLogTrace();
        TraceStatus status = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.end(status2);
        logTrace.end(status);
    }

    @Test
    public void begin_exception() throws Exception{
        FieldLogTrace logTrace = new FieldLogTrace();
        TraceStatus status = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.exception(status2, new IllegalStateException());
        logTrace.exception(status, new IllegalStateException());
    }
}