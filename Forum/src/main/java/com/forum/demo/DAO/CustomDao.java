package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.CustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDao extends JpaRepository<CustomEntity,String> {
    public CustomEntity findCustomEntityByName(String name);

}
