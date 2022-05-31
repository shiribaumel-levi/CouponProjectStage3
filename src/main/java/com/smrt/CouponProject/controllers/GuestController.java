package com.smrt.CouponProject.controllers;

import com.smrt.CouponProject.beans.Customer;
import com.smrt.CouponProject.exceptions.AdministrationException;
import com.smrt.CouponProject.exceptions.JwtException;
import com.smrt.CouponProject.exceptions.LoginException;
import com.smrt.CouponProject.repositories.CouponRepo;
import com.smrt.CouponProject.services.AdminService;
import com.smrt.CouponProject.services.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GuestController {
    @Autowired
    private GuestService guestService;
    @Autowired

    private AdminService adminService;

    @GetMapping("allCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(guestService.getAllCoupons(),HttpStatus.OK);
    }

    @GetMapping("oneCoupon/{id}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int id) {
        return new ResponseEntity<>(guestService.getOneCoupon(id),HttpStatus.OK);
    }
    @PostMapping("newCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws LoginException, JwtException, AdministrationException {
        customer.setRole("customer");
        adminService.addCustomer(customer);
        return ResponseEntity.created(URI.create("CREATED"))
                .header("")
                .body("");
    }
}
