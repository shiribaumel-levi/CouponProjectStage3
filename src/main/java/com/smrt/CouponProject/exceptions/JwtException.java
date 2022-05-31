package com.smrt.CouponProject.exceptions;

/**
 * exception thrown from inside JWTUtils. thrown when JWT is checked, and is determined invalid.
 */
public class JwtException extends Exception{
    /**
     * Blank constructor to throw "General Error" message
     */
    public JwtException() {
        super("General Error");
    }
    /**
     * Constructor to throw a custom message
     * @param message wanted message
     */
    public JwtException(String message) {
        super(message);
    }
}
