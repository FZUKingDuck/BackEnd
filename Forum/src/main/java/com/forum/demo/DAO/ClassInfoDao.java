package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassInfoDao extends JpaRepository<ClassInfoEntity,String> {
    Optional<ClassInfoEntity> findAllById(String id);
}
