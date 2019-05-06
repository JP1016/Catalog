package com.store.order.jpa.repository;

import com.store.order.jpa.entity.Catalog;
import com.store.order.model.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Catalog, Long> {

    int countAllByUserId(Long id);

    @Query("SELECT new com.store.order.model.response.OrderResponse(c.orderProduct,c.orderDescription,u.firstName,u.lastName,c.id,u.id) from Catalog c join com.store.order.jpa.entity.UserEntity u on c.userId=u.id where c.orderProduct like %:productName%")
    Page<OrderResponse> findAllOrders(@Param("productName") String productName,Pageable pageable);

}
