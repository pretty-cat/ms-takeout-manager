package com.qf.config;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 消息生产者
@Configuration
@PropertySource("classpath:mq.properties")  //制定properties文件的位置
public class RocketProducer {

    @Value("${nameServerAddress}")
    private String nameServerAddress;  // name-server的地址

    @Value("${producerGroup}")
    private String producerGroup;  //生产者组名

    @Value("${sendMailTopic}")
    private String topic;

    @Value("${sendMailTopic.tag}")
    private String tag;


    @Bean
    public ProducerConfig producerConfig() {
        ProducerConfig producerConfig = new ProducerConfig();
        producerConfig.setTopic(this.topic);
        producerConfig.setTag(this.tag);
        return producerConfig;
    }

    @Bean
    public DefaultMQProducer defaultMQProducer() {
        // 实例消息的生产者，然后通过构造方法制定其组
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(this.producerGroup);

        //设置nameServer的地址
        defaultMQProducer.setNamesrvAddr(this.nameServerAddress);

        try {
            defaultMQProducer.start();  //启动producer
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return defaultMQProducer;
    }
}
