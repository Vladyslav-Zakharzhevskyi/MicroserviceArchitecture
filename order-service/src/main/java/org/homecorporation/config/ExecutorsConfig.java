package org.homecorporation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class ExecutorsConfig {

    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(3, DEFAULT_THREAD_FACTORY);
    }

    //forkJoinPool
    @Bean
    public ExecutorService forkJoinPool() {
        return Executors.newWorkStealingPool();
    }


}
