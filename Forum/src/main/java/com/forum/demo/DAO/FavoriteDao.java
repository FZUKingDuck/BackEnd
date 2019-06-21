package com.forum.demo.DAO;

import com.forum.demo.Entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteDao extends JpaRepository<FavoriteEntity,String> {
    public List<FavoriteEntity> findFavoriteEntitiesByUserid(String userid);
}
