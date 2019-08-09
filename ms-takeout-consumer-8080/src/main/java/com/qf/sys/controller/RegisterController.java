package com.qf.sys.controller;

import com.qf.config.ProducerConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private ProducerConfig producerConfig;

    @RequestMapping
    public Object register(String email) {
        /**
         * 构建消息，第一个参数是主题，第二个参数是tag, 第三个参数是消息体部分
         */
        Message message = new Message(producerConfig.getTopic(), producerConfig.getTag(), email.getBytes());

        //Message message = new Message("email-test-topic", "notice", email.getBytes());

        try {
            defaultMQProducer.send(message);     // 消息的生产者生产的消息
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "注册成功";
    }
}
