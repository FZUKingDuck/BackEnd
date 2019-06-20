package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.ClassMemberEntity;
import jdk.internal.dynalink.support.ClassMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ClassMemberDao extends JpaRepository<ClassMemberEntity,String> {
    Optional<ClassMemberEntity> findAllByClassid(String classid);
    public List<ClassMemberEntity> findClassMemberEntitiesByClassid(String classid);


}
