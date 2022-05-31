package com.smrt.CouponProject.exceptions;

/**
 * Company exceptions for using custom exception messages while connected to company facade
 */
public class CompanyException extends Exception {
    /**
     * Blank constructor to throw "General Error" message
     */
    public CompanyException() {
        super("General Error");
    }

    /**
     * Constructor to throw a custom message
     * @param message wanted message
     */
    public CompanyException(String message) {
        super(message);
    }
}
