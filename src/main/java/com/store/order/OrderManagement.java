package com.store.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients()
@RibbonClient(name = "Order")
public class OrderManagement {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagement.class, args);
    }

}
