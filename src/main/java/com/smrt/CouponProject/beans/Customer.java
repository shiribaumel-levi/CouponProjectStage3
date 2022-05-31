package com.smrt.CouponProject.beans;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {

    // Customer ID
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Customer first name
    @Column(nullable = false, length = 15)
    private String firstName;

    // Customer last name
    @Column(nullable = false, length = 15)
    private String lastName;

    // Customer email
    @Column(nullable = false, length = 50,unique = true)
    private String email;

    // Customer password
    @Column(nullable = false, length = 25)
    private String password;

    // Customer purchased coupons list
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Singular
    private List<Coupon> coupons;

    @Column(nullable = false, length = 25)
    private String role = "customer";
}
