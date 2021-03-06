package com.crazymaker.springcloud.user.info.start;


import com.crazymaker.springcloud.standard.config.TokenFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {
        "com.crazymaker.springcloud.user",
        "com.crazymaker.springcloud.base",
        "com.crazymaker.springcloud.seckill.remote.fallback",
        "com.crazymaker.springcloud.standard"
}, exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@EnableSwagger2
@EnableJpaRepositories(basePackages = {
        "com.crazymaker.springcloud.user.*.dao",
        "com.crazymaker.springcloud.base.dao"
})

//@EnableRedisRepositories(basePackages = {
//        "com.crazymaker.springcloud.user.*.redis"})

@EntityScan(basePackages = {
        "com.crazymaker.springcloud.user.*.dao.po",
        "com.crazymaker.springcloud.base.dao.po",
        "com.crazymaker.springcloud.standard.*.dao.po"})
/**
 *  启用 Eureka Client 客户端组件
 */
@EnableEurekaClient

//启动Feign
@EnableFeignClients(basePackages =
        {"com.crazymaker.springcloud.seckill.remote.client"},
        defaultConfiguration = {TokenFeignConfiguration.class}
)

//@EnableHystrix
public class UAACloudApplication
{


    public static void main(String[] args)
    {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UAACloudApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");

        System.out.println("\n----------------------------------------------------------\n\t" +
                "UAA 用户账号与认证服务 is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "swagger-ui: \thttp://localhost:" + port + path + "/swagger-ui.html\n\t" +
                "actuator: \thttp://localhost:" + port + path + "/actuator/info\n\t" +
                "----------------------------------------------------------");
    }

}