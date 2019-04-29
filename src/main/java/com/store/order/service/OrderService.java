package com.store.order.service;

import com.store.order.exception.OrderCreationFailedException;
import com.store.order.jpa.entity.Catalog;
import com.store.order.jpa.repository.OrderRepository;
import com.store.order.model.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void postOrder(OrderRequest orderRequest) throws OrderCreationFailedException {
        log.info("Creating New Order "+orderRequest.toString());
        try {
            Catalog catalogEntity = new Catalog(orderRequest.getUserId(), orderRequest.getOrderProduct(), orderRequest.getOrderCode(), orderRequest.getOrderDescription());
            orderRepository.save(catalogEntity);
        }catch (Exception ex){
            log.error("Order Creation failed %s",ex.getMessage());
         throw new OrderCreationFailedException(ex.getMessage());
        }
    }

    public Optional<Catalog> getOrderById(Long id) {
        log.info("Getting Order by ID "+id);
        return orderRepository.findById(id);
    }

    public List<Catalog> getAllOrders() {
        log.info("Getting All Orders");
        return orderRepository.findAll();
    }

}
