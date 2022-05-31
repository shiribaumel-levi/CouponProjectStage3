package com.smrt.CouponProject.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * a data structure that holds all the client's necessary user details
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {
    private String email;
    private String password;
    private String role;
    private int id;
}
