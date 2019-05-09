package com.store.order.model.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dob;
}
