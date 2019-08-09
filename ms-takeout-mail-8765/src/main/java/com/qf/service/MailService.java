package com.qf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    // 用于发邮件的类，可以直接引入
    @Autowired
    private JavaMailSender javaMailSender;

    // thymeleaf提供的模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    public void send(String subject, String from, String to, Map<String, Object> map) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // 第二个参数是否包含多媒体数据
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setSubject(subject);  //subject是主题
            messageHelper.setFrom(from);  //表示发送方
            messageHelper.setTo(to); // 发送给谁

//            Map<String, Object> map = new HashMap() {
//                {
//                    put("logoImage", "http://img3.doubanio.com/view/subject/l/public/s33319794.jpg");
//                    put("username", "张某人");
//                    put("createTime", new Date());
//                }
//            };

            Context context = new Context();  //作用是将 thymeleaf模板需要的数据，设置进去
            context.setVariables(map);

            // 第一个参数是 templates目录下邮件的模板名。
            // 第二是实际数据，要封装到context中
            String emailContent = templateEngine.process("email", context);

            messageHelper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
