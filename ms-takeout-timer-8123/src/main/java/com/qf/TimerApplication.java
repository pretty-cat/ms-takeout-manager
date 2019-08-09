package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableScheduling   //开始定时任务
public class TimerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimerApplication.class, args);
    }
}
