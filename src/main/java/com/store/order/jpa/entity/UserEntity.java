package com.store.order.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class UserEntity {

    @Column(name = "first_name")
    public String firstName;
    @Column(name = "last_name")
    public String lastName;
    @Column(name = "dob")
    public Date dob;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public UserEntity(String firstName, String lastName, Date dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
}
