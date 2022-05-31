package com.smrt.CouponProject.controllers;

import com.smrt.CouponProject.beans.*;
import com.smrt.CouponProject.exceptions.AdministrationException;
import com.smrt.CouponProject.exceptions.JwtException;
import com.smrt.CouponProject.exceptions.LoginException;
import com.smrt.CouponProject.jwt.JWTUtils;
import com.smrt.CouponProject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
    private final String role = "Admin";
    private final AdminService adminService;
    private final JWTUtils jwtUtils;

    /**
     * verifies admin's loginDetails, and returns JWT with admin authorization upon success.
     * @param loginDetails username and password.
     * @return ResponseEntity with a JWT as it's body.
     * @throws LoginException if the userDetails aren't those of an admin.
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDetails loginDetails) throws LoginException {
        if (!adminService.login(loginDetails.getEmail(),loginDetails.getPassword())) {
            throw new LoginException("invalid user");
        }
        return new ResponseEntity<>(jwtUtils.generateToken(new UserDetails(loginDetails.getEmail(),loginDetails.getPassword(),role,0)), HttpStatus.OK);
    }

    @PostMapping("fullLogin")
    public ResponseEntity<?> fullLogin(@RequestBody LoginDetails loginDetails) throws LoginException {
        if (!adminService.login(loginDetails.getEmail(),loginDetails.getPassword())) {
            throw new LoginException("invalid user");
        }
        UserDetails myDet = new UserDetails();
        myDet.setEmail("admin@admin.com");
        myDet.setRole("admin");

        return ResponseEntity.ok()
                .header("authorization", jwtUtils.generateToken(new UserDetails(loginDetails.getEmail(),loginDetails.getPassword(),role,0)))
                .body(myDet);
    }

    /**
     * Creates company, and inserts it into the Database.
     * @param token is taken from the requestEntity's header, and used to validate the request..
     * @param company A company.
     * @return Response entity with status: CREATED.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws LoginException, JwtException ,AdministrationException{
        UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.addCompany(company);
        return ResponseEntity.created(URI.create("CREATED"))
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     *updates an existing company.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param company Updated company.
     * @throws AdministrationException if company doesn't exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @PutMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.updateCompany(company);
        return ResponseEntity.accepted()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     * deletes an existing company.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param companyId ID of the company you wish to delete.
     * @return Response entity with status:ACCEPTED.
     * @throws AdministrationException if company doesn't exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @DeleteMapping("deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.deleteCompany(companyId);
        return ResponseEntity.accepted()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     * fetches all companies from the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @return responseEntity with status:OK.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("allCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        List<Company> companies=adminService.getAllCompanies();
        companies.forEach(company->{company.setPassword("****");});
        return ResponseEntity.ok()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body(companies);
    }

    /**
     * get a single company from the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param companyId ID of the company.
     * @return responseEntity with
     * @throws AdministrationException if company ID doesnt exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("getCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        Company company=adminService.getOneCompany(companyId);
        company.setPassword("****");
        return ResponseEntity.ok()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body(company);

    }

    /**
     * adds a customer to the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param customer the customer you want to add to the database.
     * @return responseEntity with status:CREATED.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws LoginException, JwtException, AdministrationException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.addCustomer(customer);
        return ResponseEntity.created(URI.create("CREATED"))
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     * updates an existing customer
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param customer customer with updated fields.
     * @throws AdministrationException if customer ID doesnt exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @PutMapping("updateCustomer")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.updateCustomer(customer);
        return ResponseEntity.accepted()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     * deletes an existing customer, and all his coupon purchases from the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param customerId ID of customer you wish to delete.
     * @return ResponseEntity with status:ACCEPTED.
     * @throws AdministrationException if customer ID doesnt exist.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @DeleteMapping("deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        adminService.deleteCustomer(customerId);
        return ResponseEntity.accepted()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body("");
    }

    /**
     * fetches all customers from the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @return a list of all existing customers.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("allCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws  LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        List<Customer> customers= adminService.getAllCustomers();
        customers.forEach(customer -> {customer.setPassword("****");});
        return ResponseEntity.ok()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body(customers);
    }

    /**
     *  get a customer from the database.
     * @param token is taken from the requestEntity's header, and used to validate the request.
     * @param customerId ID of the customer.
     * @return a customer.
     * @throws AdministrationException if no customer with such ID exists in the database.
     * @throws LoginException when role doesn't fit.
     * @throws JwtException when JWT isn't valid.
     */
    @GetMapping("getCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) throws AdministrationException, LoginException, JwtException {
         UserDetails userDetails=jwtUtils.validateToken(token);
        if (!userDetails.getRole().equals(role)) {
            throw new LoginException("Invalid User");
        }
        Customer customer = adminService.getOneCustomer(customerId);
        customer.setPassword("****");
        return ResponseEntity.ok()
                .header("authorization", jwtUtils.generateToken(userDetails))
                .body(customer);
    }
}
