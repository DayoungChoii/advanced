package com.advanced.app.trace.hellotrace;


import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import jdk.jshell.spi.ExecutionControlProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {

    public static final String START_PREFIX = "-->";
    public static final String COMPLETE_PREFIX = "<--";
    public static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    public void end(TraceStatus startTraceStatus) {
        complete(startTraceStatus, null);
    }

    public void exception(TraceStatus startTraceStatus, Exception e) {
        complete(startTraceStatus, e);
    }

    private void complete(TraceStatus startTraceStatus, Exception e) {
        long endTimeMs = System.currentTimeMillis();
        long resultTimeMs = endTimeMs - startTraceStatus.getStartTimeMs();
        TraceId traceId = startTraceStatus.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), startTraceStatus.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), startTraceStatus.getMessage(), resultTimeMs, e.toString());
        }

    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(i == level - 1 ? "|" + prefix : "|    ");
        }
        return sb.toString();
    }
}
