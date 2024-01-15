package com.advanced.app.trace.logtrace;

import com.advanced.app.trace.TraceId;
import com.advanced.app.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    public static final String START_PREFIX = "-->";
    public static final String COMPLETE_PREFIX = "<--";
    public static final String EX_PREFIX = "<X--";

    private ThreadLocal<TraceId> traceHolder = new ThreadLocal<>(); //traceId 동기화, 동시성 이슈 발생

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceHolder.get();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceHolder.get();
        if (traceId == null) {
            traceHolder.set(new TraceId());
        } else {
            traceHolder.set(traceId.createNextId());
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
        TraceId traceId = traceHolder.get();
        if (traceId.isFirstLevel()) {
            traceHolder.remove();
        } else {
            traceHolder.set(traceId.createPreviousId());
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
