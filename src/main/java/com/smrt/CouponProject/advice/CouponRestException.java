package com.smrt.CouponProject.advice;

import com.smrt.CouponProject.beans.ErrorDetails;
import com.smrt.CouponProject.exceptions.*;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * A class that picks up exceptions from rest and returns an error message and error status without interrupt the server
 */
@RestController
@ControllerAdvice
public class CouponRestException {

    /**
     * A function that picks up Login Exception from rest and returns an error
     * message and error status without interrupt the server
     *
     * @param err Login Exception
     * @return Login Exception
     */
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetails handleException(Exception err) {
        return new ErrorDetails("Login Error", err.getMessage());
    }

    /**
     * A function that picks up Token Exception from rest and returns an error
     * message and error status without interrupt the server
     *
     * @param err Token Exception
     * @return Token Exception
     */
    @ExceptionHandler(value = {JwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetails handleJWTException(Exception err) {
        return new ErrorDetails("Token Error", err.getMessage());
    }

    /**
     * A function that picks up Purchase Exception from rest and returns an error
     * message and error status without interrupt the server
     *
     * @param err Purchase Exception
     * @return Purchase Exception
     */
    @ExceptionHandler(value = {PurchaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleCustomerException(Exception err) {
        return new ErrorDetails("Purchase Error", err.getMessage());
    }

    /**
     * A function that picks up Wrong Company Exception from rest and returns an error
     * message and error status without interrupt the server
     *
     * @param err Company Exception
     * @return Company Exception
     */
    @ExceptionHandler(value = {CompanyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleCompanyException(Exception err) {
        System.out.println("this");
        return new ErrorDetails("Company Error", err.getMessage());
    }

    /**
     * A function that picks up Administration Exception from rest and returns an error
     * message and error status without interrupt the server
     *
     * @param err Administration Exception
     * @return Administration Exception
     */
    @ExceptionHandler(value = {AdministrationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleAdminException(Exception err) {
        return new ErrorDetails("Administrator Error", err.getMessage());
    }


    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleSQLIntegrityConstraintViolationException(Exception err) {
        return new ErrorDetails("sql constraints violation.", err.getMessage());
    }
}