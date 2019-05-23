package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ExamInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamInfoDao extends JpaRepository<ExamInfoEntity,String> {
}
