package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyDao extends JpaRepository<ReplyEntity,String> {
}
