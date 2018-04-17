package io.maang.bos.mq;

import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 描述:
 * 消费
 *
 * @outhor ming
 * @create 2018-04-10 21:00
 */
@Component
public class SmsComsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;

        //调用sms服务发送短信
        try {

//            String result =
//                    SmsUtils.sendSmsByHTTP(mapMessage.getString("telephone"),
//                            mapMessage.getString("msg"));
            String result = "000/xxxx";
            if (result.startsWith("000")) {
                //发送成功
                System.out.println("发送短信成功手机号是"
                        + mapMessage.getString("telephone") +
                        "验证码是:" + mapMessage.getString("msg"));
            } else {
                //发送失败
                throw new RuntimeException("短息发送失败,信息码是:" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
