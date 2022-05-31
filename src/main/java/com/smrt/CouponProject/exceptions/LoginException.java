package com.smrt.CouponProject.exceptions;

/**
 * Login exceptions for using custom exception messages while connected to loginManager
 */
public class LoginException extends Exception {
    /**
     * Blank constructor to throw "General Error" message
     */
    public LoginException() {
        super("General Error");
    }

    /**
     * Constructor to throw a custom message
     * @param message wanted message
     */
    public LoginException(String message) {
        super(message);
    }
}
