package com.smrt.CouponProject.clr;

import com.smrt.CouponProject.beans.Coupon;
import com.smrt.CouponProject.beans.Customer;
import com.smrt.CouponProject.repositories.CompanyRepo;
import com.smrt.CouponProject.repositories.CouponRepo;
import com.smrt.CouponProject.services.AdminService;
import com.smrt.CouponProject.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.sql.Date;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.smrt.CouponProject.utils.DateUtils.getEndDate;
import static com.smrt.CouponProject.utils.DateUtils.getStartDate;
import static com.smrt.CouponProject.utils.HttpUtils.getRequest;
import static com.smrt.CouponProject.utils.NumberUtils.getAmount;
import static com.smrt.CouponProject.utils.NumberUtils.getPrice;

//@Component
@Order(4)
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    private final RestTemplate myRest;
    private Map<String, Object> map;
    private Map<String, Object> pathVariables;
    private HttpEntity<String> myRequest;
    private String myJWT;
    private ResponseEntity<String> response;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        testAll();
    }

    private void testAll() {
        adminFunctions();
        companyFunctions();
        customerFunctions();
    }

    private void adminFunctions() {
        String AdminLoginURL = "http://localhost:8080/admin/login";
        String AdminAddCompanyURL = "http://localhost:8080/admin/addCompany";
        String AdminUpdateCompanyURL = "http://localhost:8080/admin/updateCompany";
        String AdminDeleteCompanyURL = "http://localhost:8080/admin/deleteCompany/";
        String AdminAllCompanyURL = "http://localhost:8080/admin/allCompanies";
        String AdminOneCompanyURL = "http://localhost:8080/admin/getCompany/";
        String AdminAddCustomerURL = "http://localhost:8080/admin/addCustomer";
        String AdminUpdateCustomerURL = "http://localhost:8080/admin/updateCustomer";
        String AdminDeleteCustomerURL = "http://localhost:8080/admin/deleteCustomer/";
        String AdminAllCustomersURL = "http://localhost:8080/admin/allCustomers";
        String AdminOneCustomerURL = "http://localhost:8080/admin/getCustomer/";


//      Failed login
        System.out.println("\nFailed Login:");
        map = new HashMap<>();
        map.put("email", "admin");
        map.put("password", "customer");
        System.out.println("Login Details:\n" + map);
        myRequest = getRequest(map);

        try{
            response = myRest.exchange(AdminLoginURL, HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//        Failed JWT
        System.out.println("failed JWT");
        map = new HashMap<>();
        map.put("name", "newcompany");
        map.put("email","newemail@gmail.com");
        map.put("password","matan_prefer_shoes");
        String failedJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXYXNkZmFzZGZhc2ZUIn0.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaGFzZGZhc2RmYXNkZmFzZm4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.3OiXDfWv_YRfJHoqayLfNyATkc3LlLmrFPzA6goROjE.";
        myRequest = getRequest(map, failedJWT);
        try{
            response = myRest.exchange(AdminAddCompanyURL, HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //login (regular)
        System.out.println("login");
        map = new HashMap<>();
        map.put("email", "admin@admin.com");
        map.put("password","admin");
        myRequest = getRequest(map);
        myJWT = myRest.exchange(AdminLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
        System.out.println("our ADMIN JWT: "+myJWT);

//      Adding existing company (by email)
        System.out.println("Adding existing company (by email)");
        map = new HashMap<>();
        map.put("name", "MatanSocks");
        map.put("email", "matanshoes@gmail.com");
        map.put("password", "i_prefer_shoes");
        myRequest = getRequest(map, myJWT);
        try{
        response = myRest.exchange(AdminAddCompanyURL, HttpMethod.POST, myRequest, String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//      Adding existing company (by name)
        System.out.println("Adding existing company (by name)");
        map = new HashMap<>();
        map.put("name", "MatanShoes");
        map.put("email","matansocks@gmail.com");
        map.put("password","matan_prefer_shoes");
        myRequest = getRequest(map, myJWT);
        try{
        response = myRest.exchange(AdminAddCompanyURL, HttpMethod.POST, myRequest, String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//      Update company (regular)
        System.out.println("Update company (regular)");
        map = new HashMap<>();
        map.put("id",1);
        map.put("name", "SMRT");
        map.put("email", "smrt@smrt.com");
        map.put("password", "shiri_the");
        myRequest = getRequest(map, myJWT);
        response = myRest.exchange(AdminUpdateCompanyURL, HttpMethod.PUT, myRequest, String.class);
        System.out.println("\n" +response);

//      Update company (doesn't exist)
        System.out.println("Update company (doesn't exist)");
        map = new HashMap<>();
        map.put("id","6");
        map.put("name", "SMRT");
        map.put("email", "smrt@smrt.com");
        map.put("password", "shiri_the");
        myRequest = getRequest(map, myJWT);
        try{
        response = myRest.exchange(AdminUpdateCompanyURL, HttpMethod.PUT, myRequest, String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //update company (Existing email)
        System.out.println("Update company (existing email)");
        map = new HashMap<>();
        map.put("id",1);
        map.put("name", "SMRT");
        map.put("email", "matanshoes@gmail.com");
        map.put("password", "shiri_the");
        myRequest = getRequest(map, myJWT);
        try{
            response = myRest.exchange(AdminUpdateCompanyURL, HttpMethod.PUT, myRequest, String.class);
            System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //        Delete company (regular)
        System.out.println("Delete company (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminDeleteCompanyURL+"3", HttpMethod.DELETE, myRequest, String.class);
        System.out.println("\n" +response);

//        Delete company (doesn't exist)
        System.out.println("Delete company (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
        response = myRest.exchange(AdminDeleteCompanyURL+"3", HttpMethod.DELETE, myRequest, String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//        Get all companies (regular)
        System.out.println("Get all companies (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminAllCompanyURL,HttpMethod.GET,myRequest,String.class);
        System.out.println("\n" +response);

//        Get one company (regular)
        System.out.println("Get one company (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminOneCompanyURL+'1',HttpMethod.GET,myRequest,String.class);
        System.out.println("\n" +response);

//        Get one company (doesn't exist)
        System.out.println("Get one company (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
        response = myRest.exchange(AdminOneCompanyURL+'3',HttpMethod.GET,myRequest,String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//        Adding existing customer (by email)
        System.out.println("Adding existing customer (by email)");
        map = new HashMap<>();
        map.put("firstName", "Taddasdwl");
        map.put("lastName", "Harasel");
        map.put("email", "TalHarel@smrt.com");
        map.put("password", "tal_thzxcvbee_king");
        myRequest = getRequest(map,myJWT);
        try{
        response = myRest.exchange(AdminAddCustomerURL,HttpMethod.POST,myRequest,String.class);
        System.out.println("\n" +response);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

//      Update Customer (regular)
        System.out.println("Update Customer (regular)");
        map = new HashMap<>();
        map.put("id","1");
        map.put("firstName", "Talnewname");
        map.put("lastName", "Harel");
        map.put("email", "TalHarel@smrt.com");
        map.put("password", "TalNewPassword");
        myRequest = getRequest(map,myJWT);
        response = myRest.exchange(AdminUpdateCustomerURL,HttpMethod.PUT,myRequest,String.class);
        System.out.println("\n" +response);

//        Update customer (doesn't exist)
        System.out.println("Update customer (doesn't exist)");
        map = new HashMap<>();
        map.put("id","4");
        map.put("firstName", "Talnewname");
        map.put("lastName", "Harel");
        map.put("email", "TalHarel@smrt.com");
        map.put("password", "TalNewPassword");
        myRequest = getRequest(map,myJWT);
        try{
            response = myRest.exchange(AdminUpdateCustomerURL,HttpMethod.PUT,myRequest,String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

//        Delete customer (regular)
        System.out.println("Delete customer (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminDeleteCustomerURL+'3',HttpMethod.DELETE,myRequest,String.class);
        System.out.println("\n" +response);

//        Delete customer (doesn't exist)
        System.out.println("Delete customer (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
            response = myRest.exchange(AdminDeleteCustomerURL+'3',HttpMethod.DELETE,myRequest,String.class);
            System.out.println("\n" +response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

//        Get all customer (regular)
        System.out.println("Get all customer (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminAllCustomersURL,HttpMethod.GET,myRequest,String.class);
        System.out.println("\n" +response);


//        Get one customer (regular)
        System.out.println("Get one customer (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(AdminOneCustomerURL+'1',HttpMethod.GET,myRequest,String.class);
        System.out.println("\n" +response);

//        Get one customer (doesn't exist)
        System.out.println("Get one customer (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
            response = myRest.exchange(AdminOneCustomerURL+'3',HttpMethod.GET,myRequest,String.class);
            System.out.println("\n" +response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private void companyFunctions() {
        String CompanyLoginURL = "http://localhost:8080/company/login";
        String CompanyAddCouponURL = "http://localhost:8080/company/addCoupon";
        String CompanyUpdateCouponURL = "http://localhost:8080/company/updateCoupon";
        String CompanyDeleteCouponURL = "http://localhost:8080/company/deleteCoupon/";
        String CompanyCouponURL = "http://localhost:8080/company/allCoupons";
        String CompanyCouponByCategoryURL = "http://localhost:8080/company/CouponsByCategory/";
        String CompanyCouponTillMaxPriceURL = "http://localhost:8080/company/couponsTillMaxPrice/";
        String CompanyDetailURL = "http://localhost:8080/company/companyDetails";

//        Failed login
        System.out.println("\nFailed Login:");
        map = new HashMap<>();
        map.put("email", "shiri");
        map.put("password", "shiribl");
        System.out.println("Login Details:\n" + map);
        myRequest = getRequest(map);
        try{
            myJWT = myRest.exchange(CompanyLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
            System.out.println("\nJWT: " + myJWT);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

//        Failed JWT
        System.out.println("\nFailed JWT:");
        myJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        myRequest = getRequest(myJWT);
            try{
                Customer customer = myRest.exchange(CompanyDetailURL, HttpMethod.POST, myRequest, Customer.class).getBody();
                System.out.println("\n"+customer);
            }catch (Exception e) {
                System.out.println("\n" + e.getMessage());
            }


            //login
        System.out.println("\nlogin");
        map = new HashMap<>();
        map.put("email", "smrt@smrt.com");
        map.put("password", "shiri_the");
        System.out.println("Login Details:\n" + map);
        myRequest = getRequest(map);
        myJWT = myRest.exchange(CompanyLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
        System.out.println("JWT: " + myJWT);

//      Adding existing coupon (by name)
        System.out.println("Adding existing coupon (by name)");
        map = new HashMap<>();
        map.put("amount", getAmount());
        map.put("category", "ELECTRICITY");
        map.put("description", "a pair of jeans");
        map.put("endDate", getEndDate());
        map.put("image", "images/coupons/coupon");
        map.put("price", getPrice());
        map.put("startDate", getStartDate());
        map.put("title", "Computer");
        myRequest = getRequest(map, myJWT);
        try{
            response =myRest.exchange(CompanyAddCouponURL, HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//      Update coupon (regular)
        System.out.println("Update coupon (regular)");
        map = new HashMap<>();
        map.put("id",1);
        map.put("amount", getAmount());
        map.put("category", "ELECTRICITY");
        map.put("description", "A cheap computer");
        map.put("endDate", getEndDate());
        map.put("image", "images/coupons/coupon");
        map.put("price", getPrice());
        map.put("startDate", getStartDate());
        map.put("title", "Computer");
        myRequest = getRequest(map, myJWT);
        response = myRest.exchange(CompanyUpdateCouponURL, HttpMethod.PUT, myRequest, String.class);
        System.out.println("\n" +response);

//      Update coupon (doesn't exist)
        System.out.println("Update coupon (doesn't exist)");
        map = new HashMap<>();
        map.put("id",457);
        map.put("amount", getAmount());
        map.put("category", "ELECTRICITY");
        map.put("description", "A cheap computer");
        map.put("endDate", getEndDate());
        map.put("image", "images/coupons/coupon");
        map.put("price", getPrice());
        map.put("startDate", getStartDate());
        map.put("title", "Computer");
        myRequest = getRequest(map, myJWT);
        try{
            response = myRest.exchange(CompanyUpdateCouponURL, HttpMethod.PUT, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//        Delete coupon (regular)
        System.out.println("Delete coupon (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CompanyDeleteCouponURL + "2", HttpMethod.DELETE, myRequest,String.class);
        System.out.println("\n" +response);

//        Delete coupon (doesn't exist)
        System.out.println("Delete coupon (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
            response = myRest.exchange(CompanyDeleteCouponURL + "700", HttpMethod.DELETE, myRequest,String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//        Get company coupons (regular)
        System.out.println(" Get company coupons (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CompanyCouponURL, HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

//        Get company coupons by category (regular)
        System.out.println("Get company coupons by category (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CompanyCouponByCategoryURL + "FASHION", HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

//        Get company coupons by max price (regular)
        System.out.println("Get company coupons by max price (regular)");
        myRequest = getRequest(myJWT);
        response =myRest.exchange(CompanyCouponTillMaxPriceURL + "44.0", HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

//        Get company details (regular)
        System.out.println("Get company details (regular)");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CompanyDetailURL, HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

    }

    private void customerFunctions() {
        String CustomerLoginURL = "http://localhost:8080/customer/login";
        String CustomerPurchaseURL = "http://localhost:8080/customer/purchaseCoupon/";
        String CustomerCouponsURL = "http://localhost:8080/customer/customerCoupons";
        String CustomerCouponsCategoryURL = "http://localhost:8080/customer/customerCouponsByCategory/";
        String CustomerCouponsMaxPriceURL = "http://localhost:8080/customer/customerCouponsTillMaxPrice/";
        String CustomerDetailsURL = "http://localhost:8080/customer/customerDetails";
        Coupon coupon;


//      Failed login
        System.out.println("\nFailed Login:");
        map = new HashMap<>();
        map.put("email", "tal");
        map.put("password", "talking");
        System.out.println("Login Details:\n" + map);
        myRequest = getRequest(map);
        try{
            myJWT = myRest.exchange(CustomerLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
            System.out.println("\n"+myJWT);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//      Failed JWT
        System.out.println("\nFailed JWT:");
        myJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        System.out.println("JWT: " + myJWT);
        myRequest = getRequest(myJWT);
        try{
            Customer customer = myRest.exchange(CustomerDetailsURL, HttpMethod.POST, myRequest, Customer.class).getBody();
            System.out.println("\n"+customer);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//      Login
        System.out.println("\nLogin:");
        map = new HashMap<>();
        map.put("email", "TalHarel@smrt.com");
        map.put("password", "TalNewPassword");
        System.out.println("Login Details:\n" + map);
        myRequest = getRequest(map);
        myJWT = myRest.exchange(CustomerLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
        System.out.println("JWT: " + myJWT);

//      Purchase coupon (regular)
        System.out.println("\nPurchase Coupon:");
        myRequest = getRequest(myJWT);
        response =myRest.exchange(CustomerPurchaseURL + "1", HttpMethod.POST, myRequest, String.class);
        System.out.println("\n" +response);

//      Purchase coupon (doesn't exist)
        System.out.println("\nPurchase coupon (doesn't exist)");
        myRequest = getRequest(myJWT);
        try{
            response = myRest.exchange(CustomerPurchaseURL + "50", HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//      Purchase coupon (already bought)
        System.out.println("\nPurchase coupon (already bought)");
        myRequest = getRequest(myJWT);
        try{
            response =myRest.exchange(CustomerPurchaseURL + "1", HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

//      Purchase coupon (expired)
        System.out.println("\nPurchase coupon (expired)");
        myRequest = getRequest(myJWT);
        coupon = couponRepo.findById(3).get();
        coupon.setEndDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));
        couponRepo.saveAndFlush(coupon);

        try{
            response =myRest.exchange(CustomerPurchaseURL + "3", HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }


//      Purchase coupon (out of stock)
        System.out.println("\npurchase coupon (out of stock):");
        myRequest = getRequest(myJWT);
        coupon = couponRepo.getById(3);
        coupon.setAmount(0);
        couponRepo.saveAndFlush(coupon);
        try{
            response = myRest.exchange(CustomerPurchaseURL + "3", HttpMethod.POST, myRequest, String.class);
            System.out.println("\n"+response);
        }catch (Exception e) {
            System.out.println("\n" + e.getMessage());
        }

//      Get customer coupons (regular)
        System.out.println("\nget customer coupons:");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CustomerCouponsURL, HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

//      Get customer coupon by category (regular)
        System.out.println("\nget customer coupons by category:");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CustomerCouponsCategoryURL + "ELECTRICITY", HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);

//      Get customer coupon by max price (regular)
        System.out.println("\nget customer coupons by max price:");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CustomerCouponsMaxPriceURL + "34.80", HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" + response);

//      Get customer details (regular)
        System.out.println("\nget customer details:");
        myRequest = getRequest(myJWT);
        response = myRest.exchange(CustomerDetailsURL, HttpMethod.GET, myRequest, String.class);
        System.out.println("\n" +response);
    }
}
