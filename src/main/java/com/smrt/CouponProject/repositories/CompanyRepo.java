package com.smrt.CouponProject.repositories;

import com.smrt.CouponProject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {

    public boolean existsCompanyByEmailAndPassword(String email, String password);

    public Company getByEmail(String email);

}
