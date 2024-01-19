package com.advanced.app.trace.strategy;

import com.advanced.app.trace.strategy.code.ContextV2;
import com.advanced.app.trace.strategy.code.Strategy;
import com.advanced.app.trace.strategy.code.StrategyLogic1;
import com.advanced.app.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy(){
            @Override
            public void call() {
                log.info("비즈니스1 로직 실행");
            }
        });
        context.execute(new Strategy(){
            @Override
            public void call() {
                log.info("비즈니스2 로직 실행");
            }
        });
    }

    @Test
    void strategyV3() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스1 로직 실행"));
        context.execute(() -> log.info("비즈니스2 로직 실행"));
    }
}
