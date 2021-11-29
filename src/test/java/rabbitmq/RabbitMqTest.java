package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.springframework.jca.cci.connection.ConnectionFactoryUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @description
 *   rabbitmq  消息丢失的三种情况
 *      1. 生产者丢失
 *          开启RabbitMQ事务同步
 *          开启confirm模式（异步）
 *      2. MQ 丢失    开始MQ 持久化
 *
 *      3. 消费这丢失
 *          关闭rabbitMQ自动ACK
 *
 *
 *
 *
 *
 *
 * @Author beifang
 * @Create 2021/11/23 22:19
 */
public class RabbitMqTest {


    /**
     *  confirm 模式确认消息发送成功
     */
    @Test
    public void test1() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.1.7");
        factory.setPort(5672);

        try {
            // 创建链接
            Connection conn = factory.newConnection();
            // 开启通道
            Channel channel = conn.createChannel();
            // 定义对列名
            channel.queueDeclare("mq_test",false,false,false,null);
            // 开启声明
            channel.confirmSelect();
            // 构建消息
            String message = String.format("时间 =》 %s", new Date().getTime());
            // 发送消息
            channel.basicPublish("", "mq_test",null,message.getBytes(StandardCharsets.UTF_8));
            // 等待确认
            if(channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.1.7");
        factory.setPort(5672);

        Connection conn = null;
        try {
            conn = factory.newConnection();
            Channel channel = conn.createChannel();
            channel.queueDeclare("mq_test_2",false,false,false,null);
            channel.confirmSelect();
            for(int i = 0; i<10;i++) {
                String message = String.format("时间 =》" + new Date().getTime());
                channel.basicPublish("", "mq_test_2",null,message.getBytes(StandardCharsets.UTF_8));
            }

            // 直到所有消息都发送之后才会执行后面的代码，只要有一个消息未被确认就抛出 异常
            channel.waitForConfirmsOrDie();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    @Test
    public void test3() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.1.7");
        factory.setPort(5672);

        Connection conn = null;
        try {
            conn = factory.newConnection();
            Channel channel = conn.createChannel();
            channel.queueDeclare("mq_test_3",false,false,false,null);
            channel.confirmSelect();
            for(int i = 0; i<10;i++) {
                String message = String.format("时间 =》" + new Date().getTime());
                channel.basicPublish("", "mq_test_3",null,message.getBytes(StandardCharsets.UTF_8));
            }
            channel.addConfirmListener(
                    new ConfirmListener() {
                        @Override
                        public void handleAck(long l, boolean b) throws IOException {
                            System.out.println("未确认消息，标识：" + l);
                        }

                        @Override
                        public void handleNack(long l, boolean b) throws IOException {
                            System.out.println(String.format("已确认消息，标识：%d,多个消息， %b" , l, b));
                        }
                    }

            );
            System.out.println("end");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
