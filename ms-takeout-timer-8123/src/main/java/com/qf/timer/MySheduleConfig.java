package com.qf.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class MySheduleConfig implements SchedulingConfigurer {


    public Executor getExecutor() {
        return Executors.newScheduledThreadPool(3); //开启特定的任务线程，开启3个
    }

    //
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(getExecutor());
    }
}
