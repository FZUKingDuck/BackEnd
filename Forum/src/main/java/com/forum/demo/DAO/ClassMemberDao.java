package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ClassMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMemberDao extends JpaRepository<ClassMemberEntity,String> {
}
