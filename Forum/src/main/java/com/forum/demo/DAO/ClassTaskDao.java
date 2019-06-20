package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassTaskEntity;
import jdk.nashorn.internal.codegen.ClassEmitter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassTaskDao  extends JpaRepository<ClassTaskEntity,String> {

    List<ClassTaskEntity> findClassTaskEntitiesByClassidAndType(String id, String type, PageRequest page);
}
