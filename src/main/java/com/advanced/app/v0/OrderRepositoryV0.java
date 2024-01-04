package com.advanced.app.v0;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV0 {

    public void saveItem(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalArgumentException("예외 발생!");
        }

        sleep(1000);
    }

    private void sleep(int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
