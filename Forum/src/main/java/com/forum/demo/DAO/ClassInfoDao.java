package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassInfoDao extends JpaRepository<ClassInfoEntity,String> {
}
