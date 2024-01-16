package com.advanced.app.v4;

import com.advanced.app.trace.logtrace.LogTrace;
import com.advanced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepositoryV4;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepositoryV4.saveItem(itemId);
                return null;
            }
        };
        template.execute("OrderServiceV4.orderItem()");
    }
}
