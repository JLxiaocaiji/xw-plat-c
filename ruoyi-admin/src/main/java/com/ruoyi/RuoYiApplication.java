package com.ruoyi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"com.ruoyi.*"})
public class RuoYiApplication {
    public static void main(String[] args) throws UnknownHostException {
        // run变量存储了返回的ConfigurableApplicationContext实例，这是一个Spring应用程序上下文，可以用来获取Spring容器中的bean或关闭应用程序
        ConfigurableApplicationContext run = SpringApplication.run(RuoYiApplication.class, args);
        log.info("111");
        System.out.println(1111);
        // InetAddress.getLocalHost().getHostAddress(): 获取本地主机的IP地址
        log.info(startAppLog(run, InetAddress.getLocalHost().getHostAddress()));
    }


    public static String startAppLog(ConfigurableApplicationContext application, String ip) {
        System.out.println("ip");
        System.out.println(ip);
        System.out.println(application);

        Environment env = application.getEnvironment();
        // 获取Spring Boot应用程序配置中的服务器端口值，并将其转换为字符串
        String port = Objects.toString(env.getProperty("server.port"), "");
        // 获取Spring Boot应用程序配置中的服务器路径 path 值，并将其转换为字符串
        String path = Objects.toString(env.getProperty("server.servlet.context-path"), "");
        port = "80".equals(port)?"":":"+port;

        // \n 换行， \水平制表符
        String startLog = "\n\t----------------------------------------------------------\n\t" +
                "Application " + Objects.toString(env.getProperty("spring.application.name"), "") + " is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost" + port + path + "\n\t" +
                "External: \thttp://" + ip + "" + port + path + "\n\t";
        startLog = startLog + "Knife4j:  \thttp://"+ ip + port + path + "doc.html\n\t";
        startLog = startLog + "----------------------------------------------------------";
        return startLog;
    }
}
