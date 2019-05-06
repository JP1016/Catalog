package com.store.order;

import com.store.order.model.request.OrderRequest;
import com.store.order.model.request.UserRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@SpringBootApplication
@EnableFeignClients()
@RibbonClient(name = "Order")
public class OrderManagement {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagement.class, args);
    }


}
