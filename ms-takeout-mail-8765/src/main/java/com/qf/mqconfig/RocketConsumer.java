package com.qf.mqconfig;

import com.qf.service.MailService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Configuration 当使用这个注解，会将当前类纳入到Spring容器中(肯定是要实例化，肯定要调用无参构造方法)
 * 通过 @Value 来注入属性的时候，在其构造方法调用的时候并没有注入进来。
 *
 * Java反射, new 分为三步：1.开辟空间; 2.调用构造方法；3.返回地址;
 */
@Configuration
@PropertySource("classpath:mq.properties")  //制定properties文件的位置
public class RocketConsumer {

    @Value("${consumerGroup}")
    private String consumerGroup;

    @Value("${nameServerAddress}")
    private String nameServerAddress;  // name-server的地址

    @Value("${sendMailTopic}")
    private String topic;

    @Value("${sendMailTopic.tag}")
    private String tag;

    @Resource
    private MailService mailService;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        // 创建消费者，制定其消费者组
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(this.consumerGroup);

        // 设置nameServer
        defaultMQPushConsumer.setNamesrvAddr(nameServerAddress);

        // 设置消费者的 信息偏移量
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);


        try {
            // defaultMQPushConsumer.subscribe("email-test-topic", "*");  // 该主题下所有tag
            // defaultMQPushConsumer.subscribe("email-test-topic", "registerSuccess || notice");
            // email-test-topic这个主题下，tag为registerSuccess的队列。
            defaultMQPushConsumer.subscribe(this.topic, this.tag);


            /**
             * Vue中订阅。
             * PubSub.subscribe("主题名", (message, data) => {
             *
             * })
             *
             */
            defaultMQPushConsumer.registerMessageListener(new MessageListenerOrderly() {

                //表示有消息的生产者往对应的主题中生产了消息，那么该方法就会得到调用
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    msgs.forEach(me -> {
//                        System.out.println("消息的ID: " + me.getMsgId());
//                        System.out.println("主题： " + me.getTopic());
//                        System.out.println("消息的内容：" + new String(me.getBody()));
                        String email = new String(msgs.get(0).getBody());  //获取到邮件的地址

                        //当用户注册成功，是要从数据库查询其信息
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("username", "张某人");
                        map.put("logoImage", "http://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_86d58ae1.png");
                        map.put("createTime", new Date());

                        try {
                            mailService.send("test", "邮件地址", email, map);
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    return  ConsumeOrderlyStatus.SUCCESS;  //返回成功
                }
            });

            defaultMQPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return defaultMQPushConsumer;
    }
}
