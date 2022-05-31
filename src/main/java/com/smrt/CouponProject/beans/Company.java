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
@Table(name = "companies")
public class Company {
    // Company ID
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Company name
    @Column(nullable = false, length = 20,unique = true)
    private String name;

    // Company email
    @Column(nullable = false, length = 50,unique = true)
    private String email;

    // Company password
    @Column(nullable = false, length = 25)
    private String password;

    // List of the company's coupons
    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Coupon> coupons;

    @Column(nullable = false, length = 25)
    private String role = "company";
}
