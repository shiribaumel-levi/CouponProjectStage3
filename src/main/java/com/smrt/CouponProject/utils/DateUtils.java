package com.smrt.CouponProject.utils;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DateUtils {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static String getStartDate() {
        Date date = Date.from(Instant.now().minus((int) (Math.random() * 10), ChronoUnit.DAYS));
        return getFormattedDate(date);
    }

    public static String getEndDate() {
        Date date = Date.from(Instant.now().plus((int) (Math.random() * 10), ChronoUnit.DAYS));
        return getFormattedDate(date);
    }

    public static String getFormattedDate(Date date) {
        return format.format(date);
    }
}
