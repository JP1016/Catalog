package com.store.order.model.internal;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long userId;
    private String orderCode;
    private String orderProduct;
    private String orderDescription;
}
