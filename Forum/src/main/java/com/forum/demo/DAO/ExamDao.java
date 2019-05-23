package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDao extends JpaRepository<ExamEntity,String> {
}
