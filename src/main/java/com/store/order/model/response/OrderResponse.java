package com.store.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderResponse {
    public String orderProduct;
    public String orderDescription;
    public String firstName;
    public String lastName;
    public Long id;
    public Long userId;
}
