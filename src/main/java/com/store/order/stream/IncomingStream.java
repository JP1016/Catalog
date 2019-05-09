package com.store.order.stream;

import com.store.order.controller.OrderController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
@Slf4j
public class IncomingStream {

    private final String TAG = "Order Stream: ";

    @Autowired
    OrderController orderController;

    @StreamListener(target = Processor.INPUT)
    public void getUser(String userID) {
        log.info(TAG + " Fetch User with ID: " + userID);
        orderController.getUser(userID);
    }
}
