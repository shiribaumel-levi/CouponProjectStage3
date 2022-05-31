package com.smrt.CouponProject.services;

import com.smrt.CouponProject.beans.Category;
import com.smrt.CouponProject.beans.Coupon;
import com.smrt.CouponProject.beans.Customer;
import com.smrt.CouponProject.beans.Passwords;
import com.smrt.CouponProject.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ClientService {

    /**
     * checks if the login arguments are correct.
     * @param email    company email.
     * @param password company password.
     * @return customer's ID.
     */
    public int login(String email, String password) {
        if (customerRepo.existsCustomerByEmailAndPassword(email, password)) {

            return customerRepo.getCustomerByEmail(email).getId();
        }
        return 0;
    }


    /**
     * purchase a coupon.
     * @param customerID Customer's ID.
     * @param couponID Coupon's ID
     * @throws PurchaseException if purchase failed for any reason.
     */
    public void purchaseCoupon(int customerID,int couponID) throws PurchaseException {

        // First, if the coupon ID doesn't exist (=coupon doesn't exists), you can't buy that coupon
        Optional<Coupon> coupon = couponRepo.findById(couponID);
        Customer customer = customerRepo.getById(customerID);
        if (coupon.isEmpty()) {
            // An exception is thrown
            throw new PurchaseException(COUPON_NOT_EXIST_EXCEPTION+PURCHASE_EXCEPTION);
        }
        // Next, if the customer already bought that coupon, you can't buy that coupon
        if (customer.getCoupons().stream().filter(coupon1 ->coupon1.getId()==couponID).collect(Collectors.toList()).size()>0) {
            // An exception is thrown
            throw new PurchaseException(COUPON_PURCHASED_EXCEPTION+PURCHASE_EXCEPTION);
        }
        // Next, if the coupon amount is 0, you can't buy that coupon
        if (coupon.get().getAmount() <= 0) {
            // An exception is thrown
            throw new PurchaseException(COUPON_OUT_EXCEPTION+PURCHASE_EXCEPTION);
        }
        // Next, if the coupon end date already passed, you can't buy that coupon
        if (coupon.get().getEndDate().before(Date.valueOf(LocalDate.now()))) {
            // An exception is thrown
            throw new PurchaseException(COUPON_EXPIRED_EXCEPTION+PURCHASE_EXCEPTION);
        }
        // Now, we decrease the purchased coupon amount by 1
        coupon.get().setAmount(coupon.get().getAmount() - 1);
        couponRepo.saveAndFlush(coupon.get());
        // And finally purchasing the coupon
        customer.getCoupons().add(coupon.get());
        customerRepo.saveAndFlush(customer);

    }

    public List<Coupon> purchaseSomeCoupons(int customerID,int[] couponIDs)  {
        List<Coupon> coupons = new ArrayList<>();
        Customer customer = customerRepo.getById(customerID);
        for (int counter = 0; counter < couponIDs.length; counter++) {
            // First, if the coupon ID doesn't exist (=coupon doesn't exists), you can't buy that coupon
            Optional<Coupon> coupon = couponRepo.findById(couponIDs[counter]);
            if (coupon.isEmpty()) {
                // An exception is thrown
                coupon.get().setCouponErr(COUPON_NOT_EXIST_EXCEPTION + PURCHASE_EXCEPTION);
            } else
                // Next, if the customer already bought that coupon, you can't buy that coupon
                if (customer.getCoupons().stream().filter(coupon1 -> coupon1.getId() == coupon.get().getId()).collect(Collectors.toList()).size() > 0) {
                    // An exception is thrown
                    coupon.get().setCouponErr(COUPON_PURCHASED_EXCEPTION + PURCHASE_EXCEPTION);
                } else
                    // Next, if the coupon amount is 0, you can't buy that coupon
                    if (coupon.get().getAmount() <= 0) {
                        // An exception is thrown
                        coupon.get().setCouponErr(COUPON_OUT_EXCEPTION + PURCHASE_EXCEPTION);
                    } else
                        // Next, if the coupon end date already passed, you can't buy that coupon
                        if (coupon.get().getEndDate().before(Date.valueOf(LocalDate.now()))) {
                            // An exception is thrown
                            coupon.get().setCouponErr(COUPON_EXPIRED_EXCEPTION + PURCHASE_EXCEPTION);
                        } else {
                            // Now, we decrease the purchased coupon amount by 1
                            coupon.get().setAmount(coupon.get().getAmount() - 1);
                            couponRepo.saveAndFlush(coupon.get());
                            // And finally purchasing the coupon
                            customer.getCoupons().add(coupon.get());
                            customerRepo.saveAndFlush(customer);
                            coupon.get().setCouponErr("bought");
                        }
            coupons.add(coupon.get());
        }
        return coupons;
    }

    /**
     * get all purchases made by a customer.
     * @param customerID customer's ID.
     * @return list of coupons.
     */
    public List<Coupon> getCustomerCoupons(int customerID) {

        return customerRepo.getById(customerID).getCoupons();
    }

    /**
     * returns all coupons of a certain category purchased by customer.
     * @param customerID customer's ID.
     * @param category coupon's category.
     * @return list of coupons.
     */
    public List<Coupon> getCustomerCouponsByCategory(int customerID,Category category)  {

        return customerRepo.getById(customerID).getCoupons().stream().filter(coupon -> coupon.getCategory().equals(category)).collect(Collectors.toList());
    }

    /**
     * get all coupons under a specified price, purchased by a specified customer.
     * @param customerID customer's ID
     * @param maxPrice price ceiling.
     * @return list of coupons.
     */
    public List<Coupon> getCustomerCouponsTillMaxPrice(int customerID,double maxPrice)  {

        return customerRepo.getById(customerID).getCoupons().stream().filter(coupon -> coupon.getPrice()<=maxPrice).collect(Collectors.toList());
    }

    /**
     * get customer details.
     * @param customerID customer's ID.
     * @return customer details.
     */
    public Customer getCustomerDetails(int customerID) {

        return customerRepo.getById(customerID);
    }

    public Customer getByEmail(String email) {

        return customerRepo.getCustomerByEmail(email);
    }

    public void updateDetails(Customer customer) throws Exception {
        Optional<Customer> customer1 = customerRepo.findById(customer.getId());
        if (customer1.isEmpty()) {
            throw new Exception("customer dasnt exciet");
        }
        customer1.get().setEmail(customer.getEmail());
        customer1.get().setFirstName(customer.getFirstName());
        customer1.get().setLastName(customer.getLastName());
        customerRepo.saveAndFlush(customer1.get());
    }

    public void updatePassword(int id, Passwords passwords) throws Exception {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new JwtException("customer dasnt exciet");
        }
        if (!customer.get().getPassword().equals(passwords.getOldPassword())){
            throw new LoginException("wrong password");
        }

        customer.get().setPassword(passwords.getNewPassword());
        customerRepo.saveAndFlush(customer.get());
    }
}

