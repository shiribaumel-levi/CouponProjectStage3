package com.smrt.CouponProject.clr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smrt.CouponProject.beans.Company;
import com.smrt.CouponProject.beans.Details;
import com.smrt.CouponProject.beans.LoginDetails;
import com.smrt.CouponProject.repositories.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@Order(1)
@RequiredArgsConstructor
public class AddingCompanies implements CommandLineRunner {
    private final RestTemplate myRest;
    private Map<String, Object> map;
    private HttpEntity<String> myRequest;
    private final String addCompanyURL = "http://localhost:8080/admin/addCompany";
    private final String adminLoginURL = "http://localhost:8080/admin/login";


    @Override
    public void run(String... args) throws Exception {
        map = new HashMap<>();
        map.put("email", "admin@admin.com");
        map.put("password", "admin");
        myRequest = getRequest(map);
        String myJWT = myRest.postForObject(adminLoginURL, myRequest, String.class);
        System.out.println(myJWT);

        map = new HashMap<>();
        map.put("name", "SMRT");
        map.put("email", "smrt@smrt.com");
        map.put("password", "smrt");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "MatanShoes");
        map.put("email", "matanshoes@gmail.com");
        map.put("password", "matanshoes");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "HarryPotter");
        map.put("email", "harrypotter@gmail.com");
        map.put("password", "harrypotter");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "Electricity");
        map.put("email", "electricity@gmail.com");
        map.put("password", "electricity");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "Clothes");
        map.put("email", "clothes@gmail.com");
        map.put("password", "clothes");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "Grocery");
        map.put("email", "grocery@gmail.com");
        map.put("password", "grocery");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "Vacation");
        map.put("email", "vacation@gmail.com");
        map.put("password", "vacation");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);

        map = new HashMap<>();
        map.put("name", "Restaurant");
        map.put("email", "restaurant@gmail.com");
        map.put("password", "restaurant");
        myRequest = getRequest(map, myJWT);
        myRest.postForEntity(addCompanyURL, myRequest, Object.class);
    }
}
