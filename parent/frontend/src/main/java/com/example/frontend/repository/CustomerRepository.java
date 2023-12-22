package com.example.frontend.repository;

import com.example.common.entity.Customer;
import com.example.common.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("select s from Customer s where s.email = ?1")
    public Customer findByEmail(String email);
    @Query("select s from Customer s where s.verifiedCode = ?1")
    public void findByVertifiedCode(String code);
    @Query("update Customer s set s.enabled = true where s.enabled = ?1")
    @Modifying
    public void enabled(Integer id);

}
