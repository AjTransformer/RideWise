package com.airtribe.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
    private static final AtomicInteger COUNTER = new AtomicInteger(1000);

    private IdGenerator() {}

    public static String generate(String prefix) {
        return prefix + "-" + COUNTER.getAndIncrement();
    }
}
