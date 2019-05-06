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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.store.order.utils.Helper.objectHasProperty;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    public void postOrder(OrderRequest orderRequest) throws OrderCreationFailedException {
        log.info("Creating New Order " + orderRequest.toString());
        try {
            Catalog catalogEntity = new Catalog(orderRequest.getUserId(), orderRequest.getOrderProduct(), orderRequest.getOrderCode(), orderRequest.getOrderDescription());
            orderRepository.save(catalogEntity);
        } catch (Exception ex) {
            log.error("Order Creation failed %s", ex.getMessage());
            throw new OrderCreationFailedException(ex.getMessage());
        }
    }

    public Optional<Catalog> getOrderById(Long id) {
        log.info("Getting Order by ID " + id);
        return orderRepository.findById(id);
    }

//    public List<Catalog> getAllOrders() {
//        log.info("Getting All Orders");
//        return orderRepository.findAll();
//    }

    public int getOrderByUserId(Long id) {
        log.info("Getting Order by UserEntity ID " + id);
        return orderRepository.countAllByUserId(id);
    }

    public void insertUsers(List<User> users) {
        userRepository.deleteAll();
        try {
            for (User user : users) {
                UserEntity userEntity = new UserEntity(user.getFirstName(), user.getLastName(), user.getDob());
                log.info(String.format("Inserting user %s into DB ", user.getFirstName()));
                userRepository.save(userEntity);
            }
        } catch (Exception ex) {
            log.error("Failed to Create Users %s", ex.getMessage());
            throw new UserCreationFailedException(ex.getMessage());
        }
    }

    public Page<OrderResponse> findAllOrders(int pageNumber, int pageSize, String sortItem, String sortOrder, String searchKey) {


        Sort.Order sortColumn;
        if (sortOrder.equalsIgnoreCase("DESC")) {
            sortColumn = Sort.Order.desc(sortItem);
        } else {
            sortColumn = Sort.Order.asc(sortItem);
        }

        Sort sort = Sort.by(sortColumn);

        PageRequest product = PageRequest.of(pageNumber, pageSize, sort);
        Page<OrderResponse> data = orderRepository.findAllOrders(searchKey, product);
        return data;
    }
}
