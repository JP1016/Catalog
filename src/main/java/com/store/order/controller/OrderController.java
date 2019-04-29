package com.store.order.controller;

import com.store.order.exception.OrderNotFoundException;
import com.store.order.jpa.entity.Catalog;
import com.store.order.model.request.OrderRequest;
import com.store.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity order(@RequestBody OrderRequest orderRequest) {
        orderService.postOrder(orderRequest);
        log.info("Order Created");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/list")
    public ResponseEntity order() {
        log.info("Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity orderById(@PathVariable Long id) {

        Optional<Catalog> order = orderService.getOrderById(id);
        if (!order.isPresent()) {
            log.error("Order ID not found ");
            throw new OrderNotFoundException("Order Not Found");
        }
        log.info(String.format("Fetched Order with id: %d ", id));
        return ResponseEntity.ok(order);


    }
}
