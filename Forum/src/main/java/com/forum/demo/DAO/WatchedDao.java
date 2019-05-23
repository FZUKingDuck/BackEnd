package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.WatchedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedDao extends JpaRepository<WatchedEntity,String> {
}
