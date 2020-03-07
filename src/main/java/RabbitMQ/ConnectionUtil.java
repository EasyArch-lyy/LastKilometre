package RabbitMQ;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import netty.BinaryWebSocketFrameHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ConnectionUtil.class);
    
    public static Connection getConnection() {
        try {
            Connection connection = null;
            //定义一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置服务端地址（域名地址/ip）
            factory.setHost("127.0.0.1");
            //设置服务器端口号
            factory.setPort(5672);
            //设置虚拟主机(相当于数据库中的库)
            factory.setVirtualHost("/");
            //设置用户名
            factory.setUsername("guest");
            //设置密码
            factory.setPassword("guest");
            connection = factory.newConnection();
            log.info("连接建立");
            return connection;
        }
        catch (Exception e) {
            log.error("连接失败");
            return null;
        }
    }
}