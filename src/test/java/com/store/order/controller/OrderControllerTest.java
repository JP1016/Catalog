package com.store.order.controller;

import com.google.gson.Gson;
import com.store.order.exception.OrderNotFoundException;
import com.store.order.jpa.entity.Catalog;
import com.store.order.model.internal.User;
import com.store.order.model.response.OrderResponse;
import com.store.order.service.OrderService;
import com.store.order.service.UserClient;
import com.store.order.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserClient userProxy;


    private MockMvc mockMvc;

    public List<Catalog> getMockOrderList() {
        Catalog catalog1 = new Catalog(1L, 1L, "OD002", "Washing Machine", "LG");
        Catalog catalog2 = new Catalog(2L, 1L, "OD003", "Mobile", "Samsung");

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(catalog1);
        catalogList.add(catalog2);
        return catalogList;
    }

    @BeforeEach
    public void setMockOutput() {

        Mockito.lenient().when(orderService.findAllOrders(any(int.class), any(int.class), any(String.class), any(String.class), any(String.class))).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        Mockito.lenient().when(orderService.getOrderByUserId(any(Long.class))).thenReturn(1);

    }

    @Test
    void order() throws Exception {
        String json = "{\n" +
                "\"userId\" : 1,\n" +
                "\"productId\": 2,\n" +
                "\"orderDescription\" : \"Turbo Sale\",\n" +
                "\"orderProduct\" : \"Book\",\n" +
                "\"orderCode\":\"OD1249\"\n" +
                "}";
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void orderById() throws Exception {
        Mockito.lenient().when(orderService.getOrderById(any(Long.class))).thenReturn(Optional.ofNullable(getMockOrderList().get(0)));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/orders/1")
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        Gson gson = new Gson();
        Catalog order = gson.fromJson(content, Catalog.class);

        assertEquals("Washing Machine", order.getOrderProduct());
    }

    @Test
    void failedOrderById() throws Exception {

        Mockito.lenient().when(orderService.getOrderByUserId(any(Long.class))).thenThrow(new OrderNotFoundException(AppConstants.ORDER_NOT_FOUND));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/orders/1")
        ).andExpect(status().is4xxClientError()).andReturn();

        int content = result.getResponse().getStatus();

        assertEquals(404, content);
    }

    @Test
    void orderByUserId() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/orders/user/1")
        ).andExpect(status().isOk()).andReturn();

        String orderCount = result.getResponse().getContentAsString();

        assertEquals("1", orderCount);
    }

    @Test
    void getOrders() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/orders/list?page=0&size=10&sortValue=u.firstName&sortOrder=DESC&searchKey=''")
        ).andExpect(status().isOk()).andReturn();

        String pageResponse = result.getResponse().getContentAsString();

        OrderResponse orderResponse = new OrderResponse("OD", "TV", "Brad", "Max", 1L, 2L);
        List<OrderResponse> orders = new ArrayList<>();
        orders.add(orderResponse);
        Page<OrderResponse> pagedOrders = new PageImpl(orders);
        Mockito.when(orderService.findAllOrders(any(int.class), any(int.class), any(String.class), any(String.class), any(String.class))).thenReturn(pagedOrders);

        assertEquals(pagedOrders, orderService.findAllOrders(0, 10, "orderDescription", "DESC", ""));

    }


    @Test
    void getUser() {
        ZoneId defaultZoneId = ZoneId.of("Europe/Paris");
        User user1 = new User(1L, "James", "Bond", Date.from(LocalDate.of(2018, 5, 14).atStartOfDay(defaultZoneId).toInstant()));
        Mockito.lenient().when(userProxy.getUser(any(String.class))).thenReturn(user1);
        orderController.getUser("1");
        verify(orderService, times(1)).insertUser(any(User.class));
    }
}