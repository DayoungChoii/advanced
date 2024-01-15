package com.advanced.app.trace.logtrace;

import com.advanced.app.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class ThreadLocalLogTraceTest {

    ThreadLocalLogTrace logTrace = new ThreadLocalLogTrace();
    @Test
    public void begin_end() throws Exception{
        TraceStatus status = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.end(status2);
        logTrace.end(status);
    }

    @Test
    public void begin_exception() throws Exception{
        TraceStatus status = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.exception(status2, new IllegalStateException());
        logTrace.exception(status, new IllegalStateException());
    }
}
