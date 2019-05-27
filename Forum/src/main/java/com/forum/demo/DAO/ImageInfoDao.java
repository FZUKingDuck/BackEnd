package com.forum.demo.DAO;

import com.forum.demo.Entity.ImageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoDao extends JpaRepository<ImageInfoEntity,String> {
}
