package com.smrt.CouponProject.exceptions;

/**
 * Purchase exceptions for using custom exception messages while connected to customer facade
 */
public class PurchaseException extends Exception {
    /**
     * Blank constructor to throw "General Error" Message
     */
    public PurchaseException() {
        super("General Error");
    }

    /**
     * Constructor to throw a custom message
     * @param message wanted message
     */
    public PurchaseException(String message) {
        super(message);
    }
}