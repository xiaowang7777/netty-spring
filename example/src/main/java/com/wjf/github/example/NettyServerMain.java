package com.wjf.github.example;

import com.wjf.github.spring.annotion.EnableNioNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNioNettyServer
public class NettyServerMain {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettyServerMain.class, args);
    }
}
