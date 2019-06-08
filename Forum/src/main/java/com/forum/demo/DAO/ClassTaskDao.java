package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassTaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassTaskDao extends JpaRepository<ClassTaskEntity,String> {
    public List<ClassTaskEntity>  findAllByClassid(String classid, Pageable page);
    public List<ClassTaskEntity> findClassTaskEntitiesByClassidAndName(String classid,String name);
    
}
