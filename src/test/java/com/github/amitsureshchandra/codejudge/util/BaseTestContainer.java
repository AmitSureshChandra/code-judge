package com.github.amitsureshchandra.codejudge.util;

import com.redis.testcontainers.RedisContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.*;
import org.testcontainers.utility.DockerImageName;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

@Slf4j
public class BaseTestContainer {
    public static Network network = Network.newNetwork();
    public static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withNetwork(network);

    public static RabbitMQContainer rabbit = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
            .withNetwork(network);
    public static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"));

    public static GenericContainer onlineCompiler = new GenericContainer(DockerImageName.parse("online-compiler-img"))
            .withExposedPorts(8090)
            .withNetwork(network)
            .withStartupTimeout(Duration.ofSeconds(300));

    static {
        redis.start();

        rabbit.start();

        mysql.start();

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(new ClassPathResource("application.properties").getFile())) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        onlineCompiler
                //~~~~~~ env ~~~~~~~~~
                .withEnv("MQ_USERNAME", rabbit.getAdminUsername())
                .withEnv("MQ_PASS", rabbit.getAdminPassword())
                .withEnv("MQ_HOST", rabbit.getContainerInfo().getConfig().getHostName())
                .withEnv("MQ_VHOST", "/")
                .withEnv("MQ_PORT", String.valueOf(5672))
                .withEnv("REDIS_HOST", redis.getContainerInfo().getConfig().getHostName())
                .withEnv("REDIS_PORT", String.valueOf(6379))
                .withEnv("COMPILER_TMP_FOLDER", "/opt/app/temp/")
                .withEnv("HOST_TMP_FOLDER", "/home/amit/tmp/")
                //~~~~~~~~ volume binding ~~~~~~~`
                .withFileSystemBind(
                        "/var/run/docker.sock",
                        "/var/run/docker.sock",
                        BindMode.READ_WRITE
                )
                .withFileSystemBind(
                        properties.getProperty("host-tmp-path"),
                        properties.getProperty("container-tmp-path"),
                        BindMode.READ_WRITE
                );

        onlineCompiler.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {


        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        if (onlineCompiler.isCreated())
            registry.add("online-compiler-url", () -> "http://127.0.0.1:" + onlineCompiler.getMappedPort(8090));

        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.virtual-host", () -> "/");
        registry.add("spring.rabbitmq.port", () -> rabbit.getMappedPort(5672));

        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
    }
}
