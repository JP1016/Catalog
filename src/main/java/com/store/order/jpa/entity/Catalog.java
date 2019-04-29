package com.store.order.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="order_code")
    private String orderCode;
    @Column(name="order_product")
    private String orderProduct;
    @Column(name="order_description")
    private String orderDescription;


    public Catalog(Long userId, String orderProduct, String orderCode, String orderDescription) {
        this.userId = userId;
        this.orderCode = orderCode;
        this.orderProduct = orderProduct;
        this.orderDescription = orderDescription;
    }
}
