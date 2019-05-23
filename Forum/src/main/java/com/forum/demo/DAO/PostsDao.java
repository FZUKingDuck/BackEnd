package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsDao extends JpaRepository<PostsEntity,String> {
}
