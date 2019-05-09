package com.store.order.component;

import com.google.gson.Gson;
import com.store.order.controller.OrderController;
import com.store.order.jpa.entity.Catalog;
import com.store.order.jpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ComponentTest {

    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderRepository orderRepository;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        gson = new Gson();

        Catalog catalog1 = new Catalog(1L, 1L, "OD002", "Washing Machine", "LG");
        Catalog catalog2 = new Catalog(2L, 1L, "OD003", "Mobile", "Samsung");

        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(catalog1);
        catalogList.add(catalog2);

        orderRepository.saveAll(catalogList);

    }

    @Test
    public void testFlow() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/orders/1")
        ).andExpect(status().isOk()).andReturn();

        Catalog catalog = gson.fromJson(result.getResponse().getContentAsString(), Catalog.class);
        assertEquals("OD002", catalog.getOrderCode());
        assertEquals("Washing Machine", catalog.getOrderProduct());


    }


}
