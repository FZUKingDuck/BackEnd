package com.forum.demo.Controller;


import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.DAO.DownloadInfoDao;
import com.forum.demo.Entity.DownloadInfoEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/download")
public class DownloadController {
    @Autowired
    DownloadInfoDao downloadInfoDao;

    //下载文件接口
    @GetMapping(value = "/down",produces = MediaType.ALL_VALUE)
    @LogPointCut
    public byte[] down(@PathParam("downid") String downid){
        if(!StringUtils.checkKey(downid)){
            return null;
        }

        try {
            String pathHead = "/root/web/download/";
            Optional<DownloadInfoEntity> downloadCheck = downloadInfoDao.findById(downid);
            if(!downloadCheck.isPresent()){
                return null;
            }

            DownloadInfoEntity downloadInfoEntity  = downloadCheck.get();
            String url = pathHead + downloadInfoEntity.getType() +"/" +downloadInfoEntity.getPath();

            File file = new File(url);
            FileInputStream inputStream = new FileInputStream(url);

            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());

            return bytes;


        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //根据类型获取所有的下载资源列表
    @GetMapping(value = "/getDownList")
    public Result getDownList(@PathParam("type")String type){
        Result result = new Result();

        if(!StringUtils.checkKey(type)){
            result.setNullFalse();
            return result;
        }

        try{
            List<DownloadInfoEntity> list = downloadInfoDao.findDownloadInfoEntitiesByType(type);
            result.setOK("获取成功",list);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    //分页获取下载资源列表
    @GetMapping(value = "/getDownPage")
    public Result getDownPage(@PathParam("type")String type,@PathParam("pageNum")String pageNum){
        Result result = new Result();

        if(!StringUtils.checkKey(type)||!StringUtils.checkKey(pageNum)){
            result.setNullFalse();
            return result;
        }

        int num = Integer.valueOf(pageNum);
        if(num<0){
            result.setFalse(201,"页码错误");
            return result;
        }

        try{
            PageRequest page = PageRequest.of(num,10, Sort.Direction.DESC,"createtime");
            List<DownloadInfoEntity> list = downloadInfoDao.findAllByTypeIn(type,page);
            result.setOK("获取成功",list);
            return result;

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    //新增资源列表
    @PostMapping(value = "/newDown")
    @LogPointCut
    public  Result newDown(@PathParam("type")String type,@PathParam("name")String name,@PathParam("path")String path){
        Result result = new Result();

        if(!StringUtils.checkKey(type)||!StringUtils.checkKey(name)||!StringUtils.checkKey(path)){
            result.setNullFalse();
            return result;
        }

        try{
            DownloadInfoEntity downloadInfoEntity = new DownloadInfoEntity();
            downloadInfoEntity.setId(DateUtil.getIdFromDate());
            downloadInfoEntity.setName(name);
            downloadInfoEntity.setPath(path);
            downloadInfoEntity.setType(type);
            downloadInfoEntity.setOperator("000000");
            downloadInfoEntity.setCreatetime(DateUtil.getTime());
            downloadInfoEntity.setUpdatetime(DateUtil.getTime());

            downloadInfoDao.save(downloadInfoEntity);
            result.setOK("新增成功",downloadInfoEntity.getId());
            return result;

        }
        catch (Exception e){
            result.setSysFalse();
            e.printStackTrace();
            return result;
        }

    }

    //上传
    @PostMapping(value = "/upload")
    public Result upload(@PathParam("type")String type, @PathParam("name")String name, @PathParam("file")MultipartFile file){

        Result result = new Result();
        if(!StringUtils.checkKey(type)||!StringUtils.checkKey(name)||file==null){
            result.setNullFalse();
            return result;
        }

        try {

            String pathHead = "/root/web/"+type+"/";
            String path = file.getName();
            DownloadInfoEntity downloadInfoEntity = new DownloadInfoEntity();

            File dest = new File(pathHead+path);
            if(!dest.exists()){
                result.setSysFalse();
                return result;
            }

            file.transferTo(dest);
            downloadInfoEntity.setId(DateUtil.getIdFromDate());
            downloadInfoEntity.setType(type);
            downloadInfoEntity.setPath(path);
            downloadInfoEntity.setName(name);
            downloadInfoEntity.setCreatetime(DateUtil.getTime());
            downloadInfoEntity.setUpdatetime(DateUtil.getTime());
            downloadInfoEntity.setOperator("000000");

            downloadInfoDao.save(downloadInfoEntity);
            result.setOK("上传成功",true);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

}
