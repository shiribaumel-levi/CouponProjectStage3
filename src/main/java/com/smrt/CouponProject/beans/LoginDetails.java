package com.smrt.CouponProject.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * data structure that holds a client's login details.
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class LoginDetails {
    private String email;
    private String password;
}
