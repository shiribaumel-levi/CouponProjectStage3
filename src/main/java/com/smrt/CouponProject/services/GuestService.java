package com.smrt.CouponProject.services;

import com.smrt.CouponProject.beans.Coupon;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class GuestService extends ClientService {
    public List<Coupon> getAllCoupons() {
        return couponRepo.findAll();
    }

    public Coupon getOneCoupon(int id) {
        return couponRepo.getById(id);
    }
}
