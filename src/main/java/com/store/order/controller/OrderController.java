package com.store.order.controller;

import com.store.order.exception.OrderNotFoundException;
import com.store.order.jpa.entity.Catalog;
import com.store.order.model.internal.User;
import com.store.order.model.request.OrderRequest;
import com.store.order.model.response.OrderResponse;
import com.store.order.service.OrderService;
import com.store.order.service.UserClient;
import com.store.order.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserClient userProxy;


    @PostMapping("/orders")
    public ResponseEntity order(@RequestBody OrderRequest orderRequest) {
        orderService.postOrder(orderRequest);
        log.info("Order Created");
        return ResponseEntity.ok().build();
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

    @GetMapping("/orders/user/{id}")
    public ResponseEntity orderByUserId(@PathVariable Long id) {
        int order = orderService.getOrderByUserId(id);

        log.info(String.format("Fetching Number of orders by user"));
        return ResponseEntity.ok(order);

    }

    @GetMapping("/orders/list")
    public ResponseEntity test(
            @RequestParam(value = AppConstants.PAGE,defaultValue = AppConstants.DEFAULT_PAGE_NUM) int pageNum,
            @RequestParam(value = AppConstants.SIZE,defaultValue = AppConstants.DEFAULT_ITEMS_PER_PAGE) int itemsPerPage,
            @RequestParam(value = AppConstants.SORT_ITEM,required = false,defaultValue = AppConstants.DEFAULT_SORT_ITEM) String sortItem,
            @RequestParam(value=AppConstants.SORT_ORDER,required = false,defaultValue = AppConstants.ASCENDING_ORDER) String sortOrder,
            @RequestParam(value = AppConstants.SEARCH_KEY,required = false,defaultValue = AppConstants.DEFAULT_SEARCH_KEY) String searchKey){
        log.info("Page: "+pageNum+" Size: "+itemsPerPage+" sortItem: "+sortItem+" sortOrder: "+sortOrder+" searchKey: "+searchKey);
        Page<OrderResponse> allOrders = orderService.findAllOrders(pageNum, itemsPerPage, sortItem, sortOrder, searchKey);
        return ResponseEntity.ok(allOrders);
    }

    public void getUsers(){
        List<User> users=userProxy.getUsers();
        orderService.insertUsers(users);
    }

}
