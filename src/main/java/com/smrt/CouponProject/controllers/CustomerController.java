package com.smrt.CouponProject.controllers;

import com.smrt.CouponProject.beans.*;
import com.smrt.CouponProject.exceptions.*;
import com.smrt.CouponProject.services.CustomerService;
import com.smrt.CouponProject.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController{

    private final CustomerService customerService;
    private final JWTUtils jwtUtils;
    private String role = "Customer";

    /**
     * logs customer into the system.
     * @param loginDetails email and password.
     * @return JWT that authorizes use of customerController methods.
     * @throws LoginException if customerID doesn't exist.
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDetails loginDetails) throws LoginException {
        int customerID = customerService.login(loginDetails.getEmail(), loginDetails.getPassword());
        if (customerID == 0) {
            throw new LoginException("invalid user");
        }
        return new ResponseEntity<>(jwtUtils.generateToken(new UserDetails(loginDetails.getEmail(),
                        loginDetails.getPassword(), this.role, customerID)),
                HttpStatus.OK);
    }

    @PostMapping("fullLogin")
    public ResponseEntity<?> fullLogin(@RequestBody LoginDetails loginDetails) throws LoginException {
        int customerID = customerService.login(loginDetails.getEmail(), loginDetails.getPassword());
        if (customerID == 0) {
            throw new LoginException("invalid user");
        }
        Customer customer = customerService.getByEmail(loginDetails.getEmail());
        customer.setPassword("****");
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(new UserDetails(loginDetails.getEmail(),
                        loginDetails.getPassword(), this.role, customerID)))
                .body(customer);
    }

    /**
     * purchase a coupon.
     * @param token is taken from the requestEntity's header, and used to validate the request
     * @param couponId ID of coupon.
     * @throws PurchaseException if couponID doesn't exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @PostMapping("purchaseCoupon/{couponId}")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int couponId) throws PurchaseException, JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        customerService.purchaseCoupon(userDetails.getId(),couponId);
        return ResponseEntity.created(URI.create("created"))
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body("");

    }

    @PostMapping("purchaseSomeCoupons")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody int[] IDs) throws PurchaseException, JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        return ResponseEntity.created(URI.create("created"))
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body(customerService.purchaseSomeCoupons(userDetails.getId(),IDs));

    }


    /**
     * get all coupons owned by customer.
     * @param token is taken from the requestEntity's header, and used to validate the request
     * @return list of all coupons owned by customer.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("customerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        return (ResponseEntity<?>) ResponseEntity.accepted()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body(customerService.getCustomerCoupons(userDetails.getId()));
    }

    /**
     * get all coupons owned by customer, filtered by category.
     * @param token is taken from the requestEntity's header, and used to validate the request
     * @param categoryId category's ID
     * @return list of coupons owned by customer, filtered by category.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("customerCouponsByCategory/{categoryId}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category categoryId) throws JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        return (ResponseEntity<?>) ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body(customerService.getCustomerCouponsByCategory(userDetails.getId(),categoryId));
    }

    /**
     * get coupons owned by customer, till a certain price.
     * @param token is taken from the requestEntity's header, and used to validate the request
     * @param maxPrice max price.
     * @return list of coupons owned by customer, till a certain price.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("customerCouponsTillMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsTillMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        return (ResponseEntity<?>) ResponseEntity.accepted()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body(customerService.getCustomerCouponsTillMaxPrice(userDetails.getId(),maxPrice));
    }

    /**
     * get customer details.
     * @param token is taken from the requestEntity's header, and used to validate the request, and fetch the customer's details.
     * @return customer details.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("customerDetails")
    public ResponseEntity<?> customerDetails(@RequestHeader(name = "Authorization") String token) throws JwtException, LoginException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid user");
        }
        Customer customer = customerService.getCustomerDetails(userDetails.getId());
        customer.setPassword("****");
        return (ResponseEntity<?>) ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body(customer);
    }

    @PutMapping("updateDetails")
    public ResponseEntity<?> updateDetails(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws LoginException,  Exception{
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        customer.setId(userDetails.getId());
        customerService.updateDetails(customer);
        return ResponseEntity.accepted()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    @PutMapping("updatePassword")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> updatePassword(@RequestHeader(name = "Authorization") String token, @RequestBody Passwords passwords) throws LoginException,  Exception{
        UserDetails userDetails = jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        customerService.updatePassword(userDetails.getId(), passwords);
        return ResponseEntity.accepted()
                .header("Authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }
}
