package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ReplyDao extends JpaRepository<ReplyEntity,String> {
    public void deleteReplyEntitiesByPostsid(String posts);
    public Page<ReplyEntity> findAllByPostsidIn(String postsid, Pageable page);
    public void deleteByPostsidEquals(String posts);
    public Page<ReplyEntity> findAllByUserIn(String user,Pageable page);

}
