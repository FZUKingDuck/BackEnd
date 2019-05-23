package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalkDao extends JpaRepository<TalkEntity,String> {
}
