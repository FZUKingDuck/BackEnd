package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendDao extends JpaRepository<FriendEntity,String> {
}
