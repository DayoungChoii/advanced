package com.advanced.app.trace.hellotrace;

import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

    @Test
    public void beginSync_end() throws Exception{
        HelloTraceV2 helloTraceV2 = new HelloTraceV2();
        TraceStatus status = helloTraceV2.begin("hello");
        TraceStatus status2 = helloTraceV2.beginSync(status.getTraceId(),"hello");
        helloTraceV2.end(status2);
        helloTraceV2.end(status);
    }

    @Test
    public void beginSync_exception() throws Exception{
        HelloTraceV2 helloTraceV2 = new HelloTraceV2();
        TraceStatus status = helloTraceV2.begin("hello");
        TraceStatus status2 = helloTraceV2.beginSync(status.getTraceId(),"hello");
        helloTraceV2.exception(status2, new Exception("예외발생!!"));
        helloTraceV2.exception(status, new Exception("예외발생!!"));
    }

}