package com.forum.demo.DAO;

import com.forum.demo.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<CustomerEntity,String> {
    public CustomerEntity findCustomerEntityByName(String name);
}
