package com.smrt.CouponProject.clr;

import com.smrt.CouponProject.beans.Customer;
import com.smrt.CouponProject.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smrt.CouponProject.utils.HttpUtils.*;

@Component
@Order(2)
@RequiredArgsConstructor
public class AddingCustomers implements CommandLineRunner {
    private final RestTemplate myRest;
    private Map<String, Object> map;
    private HttpEntity<String> myRequest;
    private final String addCustomerURL = "http://localhost:8080/admin/addCustomer";
    private final String adminLoginURL = "http://localhost:8080/admin/login";

    @Override
    public void run(String... args) throws Exception {
        map = new HashMap<>();
        map.put("email", "admin@admin.com");
        map.put("password", "admin");
        myRequest = getRequest(map);
        String myJWT = myRest.postForObject(adminLoginURL, myRequest, String.class);

        map = new HashMap<>();
        map.put("firstName", "Tal");
        map.put("lastName", "Harel");
        map.put("email", "TalHarel@smrt.com");
        map.put("password", "tal_the_king");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCustomerURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("firstName", "Shiri");
        map.put("lastName", "Levi");
        map.put("email", "ShiriLevy@smrt.com");
        map.put("password", "shiri_the_queen");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCustomerURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("firstName", "Matan");
        map.put("lastName", "Ozer");
        map.put("email", "MatanOzer@smrt.com");
        map.put("password", "matan_the_king");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCustomerURL, myRequest, Object.class);
    }
}
