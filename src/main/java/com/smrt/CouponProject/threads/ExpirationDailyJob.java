package com.smrt.CouponProject.threads;

import com.smrt.CouponProject.repositories.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@EnableAsync
@EnableScheduling
public class ExpirationDailyJob {

    @Autowired
    CouponRepo couponRepo;

    /**
     * deletes all expired coupons, and deletes all their purchase instances from the database.
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Jerusalem")
    public void deleteByEndDateBefore() {
        couponRepo.deleteByEndDateBefore(Date.valueOf(LocalDate.now()));
    }
}
