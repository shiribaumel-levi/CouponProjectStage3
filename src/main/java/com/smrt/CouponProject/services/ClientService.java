package com.smrt.CouponProject.services;

import com.smrt.CouponProject.repositories.CompanyRepo;
import com.smrt.CouponProject.repositories.CouponRepo;
import com.smrt.CouponProject.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    CompanyRepo companyRepo;


    protected final String NOT_LOGGED_EXCEPTION = "Not logged in";

    protected final String CUSTOMER_NOT_EXIST_EXCEPTION = "Customer doesn't exist";
    protected final String COMPANY_NOT_EXIST_EXCEPTION = "Company doesn't exist";
    protected final String COUPON_NOT_EXIST_EXCEPTION = "Coupon doesn't exists";

    protected final String COUPON_PURCHASED_EXCEPTION = "You already purchased the coupon";
    protected final String COUPON_OUT_EXCEPTION = "The coupon is out of stock";
    protected final String COUPON_EXPIRED_EXCEPTION = "The coupon is expired";

    protected final String UPDATE_EXCEPTION = ", can't update";
    protected final String DELETE_EXCEPTION = ", can't delete";
    protected final String PURCHASE_EXCEPTION = ", can't purchase";
}
