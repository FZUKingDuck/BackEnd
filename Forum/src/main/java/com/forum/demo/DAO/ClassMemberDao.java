package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ClassMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassMemberDao extends JpaRepository<ClassMemberEntity,String> {
    Optional<ClassMemberEntity> findAllByClassid(String id);

    List<ClassMemberEntity> findClassMemberEntitiesByClassid(String id);

    ClassMemberEntity findClassMemberEntityByUser(String id);
}
