package com.smrt.CouponProject.utils;

public class NumberUtils {
    public static int getAmount() {
        return (int) (Math.random() * 16 + 10);
    }

    public static double getPrice() {
        return (int)(Math.random() * 19) * 5 + 10;
    }
}
