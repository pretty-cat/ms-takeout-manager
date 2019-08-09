package com.qf.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedule {


    /**
     *  fixedRate: 固定时间去执行。
     *  fixedDelay: 固定的延迟。
     *
     *  cron有六个站位符号： 第一个表示秒，第二个是分，第三个小时，第四是日，
     *   第五个是月份，第六个是星期
     *   0/2 * * * * *  每两秒执行一次
     *   0 25 11 * * * 每天11:25执行
     *   0 0/1 11 * * *  每天的11点，每隔5分钟执行一次
     *   0 0 20 * * *  每天晚上8点钟执行
     *   0 0 8,20 * * * 每天早晚8点执行一次
     *   0 0 8-20 * * * 每天早上8点到晚8点，每个小时执行一次
     *   0 0 12 L * * * 每个月的最后一天12钟执行。
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void doSomething() {
        System.out.println(Thread.currentThread().getName() + "=========================");
        System.out.println("定时任务开始................");
    }

    @Scheduled(cron = "0/3 * * * * *")
    public void doSomething1() {
        System.out.println(Thread.currentThread().getName() + "**********");
        System.out.println("定时每分钟执行一次，任务开始................");
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void doSomething2() {
        System.out.println(Thread.currentThread().getName() + "&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("定时任务每天早8点到晚8点，每20分钟执行一次，开始................");
    }

}
