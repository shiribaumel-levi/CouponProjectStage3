package com.smrt.CouponProject.repositories;

import com.smrt.CouponProject.beans.Category;
import com.smrt.CouponProject.beans.Company;
import com.smrt.CouponProject.beans.Coupon;
import com.smrt.CouponProject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    public boolean existsCustomerByEmailAndPassword(String email, String password);

    public Customer getCustomerByEmail(String email);

    public boolean existsCustomerByEmail(String email);
}
