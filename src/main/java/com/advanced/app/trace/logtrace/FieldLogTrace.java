package com.advanced.app.trace.logtrace;

import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace{

    public static final String START_PREFIX = "-->";
    public static final String COMPLETE_PREFIX = "<--";
    public static final String EX_PREFIX = "<X--";

    private TraceId traceHolder; //traceId 동기화, 동시성 이슈 발생

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceHolder;
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        if (traceHolder == null) {
            traceHolder = new TraceId();
        } else {
            traceHolder = traceHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus startTraceStatus) {
        complete(startTraceStatus, null);
    }

    @Override
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

        releaseTraceId();

    }

    private void releaseTraceId() {
        if (traceHolder.isFirstLevel()) {
            traceHolder = null;
        } else {
            traceHolder = traceHolder.createPreviousId();
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
