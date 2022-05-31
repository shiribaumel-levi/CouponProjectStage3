package com.smrt.CouponProject.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupons")
public class Coupon {

    // Coupon ID
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ID of company owning this coupon
    @Column(name = "company_id", nullable = false)
    private int companyID;

    // Coupon category
    @Column(name = "category_id", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    // Coupon title (name)
    @Column(nullable = false, length = 20)
    private String title;

    // Coupon description
    @Column(nullable = false, length = 100)
    private String description;

    // Coupon start date
    @Column(nullable = false)
    private Date startDate;

    // Coupon end date
    @Column(nullable = false)
    private Date endDate;

    // Coupon amount left
    @Column(nullable = false)
    private int amount;

    // Coupon price
    @Column(nullable = false)
    private double price;

    // Coupon image URL
    @Column(nullable = false)
    private String image;

    private String couponErr;
}
