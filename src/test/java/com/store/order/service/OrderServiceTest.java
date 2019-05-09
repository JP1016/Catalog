package com.store.order.service;

import com.store.order.exception.OrderCreationFailedException;
import com.store.order.exception.UserCreationFailedException;
import com.store.order.jpa.entity.Catalog;
import com.store.order.jpa.entity.UserEntity;
import com.store.order.jpa.repository.OrderRepository;
import com.store.order.jpa.repository.UserRepository;
import com.store.order.model.internal.User;
import com.store.order.model.request.OrderRequest;
import com.store.order.model.response.OrderResponse;
import com.store.order.utils.AppConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Spy
    private OrderRepository orderRepository;

    @Spy
    private UserRepository userRepository;


    @Test
    void postOrder() {
        OrderRequest orderRequest=new OrderRequest(1L,1L,"LG","Washing Maching","OD123");
        Catalog catalog1 = new Catalog(1L, 1L, "OD002", "Washing Machine", "LG");
        Mockito.lenient().when(orderRepository.save(any(Catalog.class))).thenReturn(catalog1);
        orderService.postOrder(orderRequest);
        verify(orderRepository,times(1)).save(any(Catalog.class));

    }

    @Test
    void failedPostOrder() {
        OrderRequest orderRequest=new OrderRequest(1L,1L,"LG","Washing Maching","OD123");
        Mockito.lenient().when(orderRepository.save(any(Catalog.class))).thenThrow(new OrderCreationFailedException(AppConstants.ORDER_CREATION_FAILED));
        assertThrows(OrderCreationFailedException.class,()->orderService.postOrder(orderRequest));

    }

    @Test
    void getOrderById() {
        Catalog catalog1 = new Catalog(1L, 1L, "OD002", "Washing Machine", "LG");
        Mockito.lenient().when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(catalog1));
        assertEquals("OD002", orderService.getOrderById(2L).get().getOrderCode());
        assertEquals("LG", orderService.getOrderById(2L).get().getOrderDescription());
        assertEquals("Washing Machine", orderService.getOrderById(2L).get().getOrderProduct());
    }

    @Test
    void getOrderByUserId() {
        Mockito.lenient().when(orderRepository.countAllByUserId(any(Long.class))).thenReturn(1);
        assertEquals(1,orderService.getOrderByUserId(1L));
    }


    @Test
    void insertUser() {
        ZoneId defaultZoneId =ZoneId.of("Europe/Paris");
        User user1 = new User(1L, "James", "Bond", Date.from(LocalDate.of(2018, 5, 14).atStartOfDay(defaultZoneId).toInstant()));
        UserEntity userEntity = new UserEntity("James", "Bond", Date.from(LocalDate.of(2018, 5, 14).atStartOfDay(defaultZoneId).toInstant()),1L);
        Mockito.lenient().when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        orderService.insertUser(user1);
        verify(userRepository,times(1)).save(any(UserEntity.class));
    }

    @Test
    void failedInsertUser() {
        ZoneId defaultZoneId =ZoneId.of("Europe/Paris");
        User user1 = new User(1L, "James", "Bond", Date.from(LocalDate.of(2018, 5, 14).atStartOfDay(defaultZoneId).toInstant()));
        Mockito.lenient().when(userRepository.save(any(UserEntity.class))).thenThrow(new UserCreationFailedException(AppConstants.FAILED_USER_CREATION));
        assertThrows(UserCreationFailedException.class,()->orderService.insertUser(user1));
    }

    @Test
    void findAllOrders() {
        OrderResponse orderResponse=new OrderResponse("OD","TV","Brad","Max",1L,2L);
        List<OrderResponse> orders = new ArrayList<>();
        orders.add(orderResponse);
        Page<OrderResponse> pagedOrders = new PageImpl(orders);
        Mockito.lenient().when(orderRepository.findAllOrders(any(String.class),any(Pageable.class))).thenReturn(pagedOrders);

        Page<OrderResponse> allOrders = orderService.findAllOrders(0, 10, "orderDescription", "DESC", "");

        assertEquals(pagedOrders.getTotalElements(),allOrders.getTotalElements());
        assertEquals(pagedOrders.get().toArray()[0].toString(),allOrders.get().toArray()[0].toString());
    }
}