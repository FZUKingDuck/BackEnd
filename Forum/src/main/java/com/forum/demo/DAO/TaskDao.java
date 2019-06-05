package com.forum.demo.DAO;

import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.Entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TaskDao extends JpaRepository<TaskEntity,String> {
    public List<TaskEntity> findAllByTypeIn(String type, Pageable page);
}
