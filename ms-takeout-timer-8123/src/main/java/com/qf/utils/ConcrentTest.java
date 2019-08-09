package com.qf.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcrentTest {
    public static void main(String[] args) {


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);


        /**
         * 第一个参数是要执行的任务，
         * 第二个参数是从程序启动，到第一次开始执行间隔的事件
         * 第三个参数 间隔事件
         * 第四个参数是  时间单位
         */
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            System.out.println("---------------------------------");

        }, 10, 5, TimeUnit.SECONDS);
    }
}
