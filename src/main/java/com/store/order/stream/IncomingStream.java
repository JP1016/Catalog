package com.store.order.stream;

import com.store.order.controller.OrderController;
import com.store.order.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
public class IncomingStream {

    @Autowired
    OrderController orderController;

    @StreamListener(target=Processor.INPUT)
    public void getUser(String action) {
        if(action.equals(AppConstants.CREATE_USER)){
            orderController.getUsers();
        }
    }
}
