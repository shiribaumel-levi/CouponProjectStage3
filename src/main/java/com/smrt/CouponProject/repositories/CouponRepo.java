package com.smrt.CouponProject.repositories;

import com.smrt.CouponProject.beans.Category;
import com.smrt.CouponProject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    public List<Coupon> findByCompanyID(int companyID);

    public boolean existsCouponByCompanyIDAndTitle(int companyID, String title);

    public Optional<Coupon> findByCompanyIDAndTitle(int companyID, String title);

    public List<Coupon> findByCompanyIDAndCategory(int companyID, Category category);

    public List<Coupon> findByCompanyIDAndPriceLessThanEqual(int companyID, double maxPrice);

    public void deleteByEndDateBefore(Date date);

    public void deleteByCompanyID(int companyID);


}
