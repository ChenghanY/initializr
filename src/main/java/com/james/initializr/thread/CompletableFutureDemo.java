package com.james.initializr.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        IntStream.range(1, 30).forEach(i -> lines.add("hi" + i));

        CompletableFuture.allOf(lines.stream()
                .map(line -> CompletableFuture.runAsync(() -> {
                    //do something async
                    System.out.println(line);
                }, ThreadPoolHolder.THREAD_POOL))
                .toArray(CompletableFuture[]::new))
                .join();

        System.out.println("all done");
    }
}
