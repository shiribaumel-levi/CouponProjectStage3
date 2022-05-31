package com.smrt.CouponProject.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  error details which we pass to http responses as a body when an error on client side arises.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String error;
    private String description;
}
