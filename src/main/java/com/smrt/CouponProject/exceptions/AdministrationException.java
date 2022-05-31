package com.smrt.CouponProject.exceptions;

/**
 * Administration exceptions for using custom exception messages while connected to admin facade
 */
public class AdministrationException extends Exception {
    /**
     * Blank constructor to throw "General Error" message
     */
    public AdministrationException() {
        super("General Error");
    }

    /**
     * Constructor to throw a custom message
     * @param message wanted message
     */
    public AdministrationException(String message) {
        super(message);
    }
}
