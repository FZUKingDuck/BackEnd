package com.forum.demo.DAO;

import com.forum.demo.Entity.DownloadInfoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownloadInfoDao extends JpaRepository<DownloadInfoEntity,String> {
    public List<DownloadInfoEntity> findDownloadInfoEntitiesByType(String type);
    public List<DownloadInfoEntity> findAllByTypeIn(String type, Pageable page);

}
