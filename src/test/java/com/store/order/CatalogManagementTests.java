package com.store.order;

import com.store.order.jpa.entity.Catalog;
import com.store.order.jpa.repository.OrderRepository;
import com.store.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CatalogManagementTests {

    @InjectMocks
    private OrderService orderService;

    @Spy
    private OrderRepository orderRepository;


    @BeforeEach
    public void setMockOutput() {
        Catalog catalog1 = new Catalog(1L, 1L, "OD002", "Washing Machine", "LG");
        Catalog catalog2 = new Catalog(2L, 1L, "OD003", "Mobile", "Samsung");

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(catalog1);
        catalogList.add(catalog2);

        Mockito.lenient().when(orderRepository.findAll()).thenReturn(catalogList);
        Mockito.lenient().when(orderRepository.findById(2L)).thenReturn(Optional.of(catalog2));

    }


    @DisplayName("Order Details Testing")
    @Test
    public void checkOrderDetails() {
        assertEquals("OD003", orderService.getOrderById(2L).get().getOrderCode());
        assertEquals("Samsung", orderService.getOrderById(2L).get().getOrderDescription());
        assertEquals("Mobile", orderService.getOrderById(2L).get().getOrderProduct());

        assertEquals(2,orderRepository.findAll().size());
    }


}