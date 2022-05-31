package com.smrt.CouponProject.clr;

import com.smrt.CouponProject.beans.Coupon;
import com.smrt.CouponProject.beans.Customer;
import com.smrt.CouponProject.beans.UserDetails;
import com.smrt.CouponProject.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.smrt.CouponProject.utils.HttpUtils.*;

@Component
@Order(4)
@RequiredArgsConstructor
public class AddingRandomCustomers implements CommandLineRunner {
    private final RestTemplate myRest;
    private Map<String, Object> map;
    private HttpEntity<String> myRequest;

    @Override
    public void run(String... args) throws Exception {
        map = new HashMap<>();
        for (int counter=0; counter<100; counter++){
        Customer customer = getRandomCustomer();
        Register(customer);
        String myJWT=LoginForRandomCus(customer);
        PurchaseRandomCupons(myJWT);}
    }

    public Customer getRandomCustomer(){
        String URL="https://randomuser.me/api/";
        Customer customer = new Customer();
        try{
        ResponseEntity<String> response= myRest.getForEntity(URL,String.class);
        String myString =response.getBody().split("=")[0];

        String fName =myString.split(":")[5].split("\"")[1];
        String lName =myString.split(":")[6].split("\"")[1];
        String email =myString.split("\"email\":")[1].split("\"")[1];

        customer.setPassword("1234");
        customer.setEmail(email);
        customer.setFirstName(fName);
        customer.setLastName(lName);
        }catch(Exception e){
            System.out.println("********************* you luser to get random names");
        }
        return (customer);
    }

    private void Register (Customer customer){
        map.put("firstName", customer.getFirstName());
        map.put("lastName", customer.getLastName());
        map.put("email", customer.getEmail());
        map.put("password", customer.getPassword());
        myRequest = getRequest(map);
        String addGuestCustomerURL = "http://localhost:8080/newCustomer";
        try {
            myRest.postForEntity(addGuestCustomerURL, myRequest, Object.class);
        }catch (Exception e){
            System.out.println("********************* you luser to register");
        }
    }

    private String LoginForRandomCus(Customer customer) {
        String CustomerLoginURL = "http://localhost:8080/customer/login";
        String myJWT="";
        map = new HashMap<>();
        map.put("email", customer.getEmail());
        map.put("password", customer.getPassword());
        myRequest = getRequest(map);
        try {
            myJWT = myRest.exchange(CustomerLoginURL, HttpMethod.POST, myRequest, String.class).getBody();
        } catch (Exception e) {
            System.out.println("********************* you luser to logedIn");
        }
        return (myJWT);
        }


    private void PurchaseRandomCupons(String myJWT) {
        String CustomerPurchaseURL = "http://localhost:8080/customer/purchaseCoupon/";
        int number = (int) (Math.random() * 15);
        myRequest = getRequest(myJWT);

            for (int coupon =0; coupon<number; coupon++) {
              int randomCoupon = (int) (Math.random() * 100);
              try {
                    myRest.exchange(CustomerPurchaseURL+randomCoupon, HttpMethod.POST, myRequest, String.class);
                    }catch (Exception e) {

                    }
            }
    }

}
