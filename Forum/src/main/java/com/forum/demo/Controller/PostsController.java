package com.forum.demo.Controller;


import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.PostsDao;
import com.forum.demo.DAO.TaskDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import javafx.geometry.Pos;
import org.hibernate.dialect.CUBRIDDialect;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value =  "/posts")
public class PostsController {

    @Autowired
    CustomDao customDao;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    PostsDao postsDao;

    @Autowired
    TaskDao taskDao;


    //获取帖子的列表
    //type：帖子的类型    pageNumber：页码
    //返回指定类型的帖子的数组
    @GetMapping(value = "/getPostsList")
    public Result getPostsList(@PathParam("type")String type,@PathParam("pageNumber")String pageNumber){
        Result result = new Result();

        if(!StringUtils.checkKey(type)||!StringUtils.checkKey(pageNumber)){
            result.setNullFalse();
            return result;
        }

        try{
            int number = Integer.valueOf(pageNumber);
            if(number<1){
                result.setFalse(201,"页码错误");
                return result;
            }

            //创建根据发帖时间排序的分页查询规则
            PageRequest page = PageRequest.of(number,15, Sort.Direction.DESC,"creattime");
            //获取数据，返回
            List<PostsEntity> list = postsDao.findAllByTypeIn(type,page);
            result.setOK("ok",list);
            return result;

        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    //获取所有帖子的数据
    //不需要指定帖子的类型
    //pageNumber：页码
    @GetMapping(value = "/getPostsListAll")
    public Result getPostsListAll(@PathParam("pageNumber")String pageNumber){
        Result result  = new Result();

        if(!StringUtils.checkKey(pageNumber)){
            result.setNullFalse();
            return result;
        }

        try{
            int number = Integer.valueOf(pageNumber);
            if(number<1){
                result.setFalse(201,"页码错误");
                return result;
            }

            //创建根据发帖时间排序的分页查询规则
            PageRequest page = PageRequest.of(number,15, Sort.Direction.DESC,"creattime");
            //获取数据，返回
            Page<PostsEntity> res = postsDao.findAll(page);
            if(!res.hasContent()){
                result.setSysFalse();
                return result;
            }
            List<PostsEntity> list = res.getContent();
            result.setOK("ok",list);
            return result;

        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    //获取指定帖子的信息

    @GetMapping(value = "/getPostsInfo")
    public Result getPostsInfo(@PathParam("id")String id) {
        Result result = new Result();

        if(StringUtils.checkKey(id)){
            result.setNullFalse();
            return result;
        }


        Optional<PostsEntity> res = postsDao.findById(id);
        if(res==null||!res.isPresent()){
            result.setFalse(201,"无此帖子");
            return result;

        }
        PostsEntity postsEntity = res.get();

        result.setOK("获取成功",postsEntity);
        return result;

    }

    //发帖
    @MonitorRequest
    @PostMapping(value = "/newPosts")
    public Result newPosts(@PathParam("id")String id,@PathParam("userid")String userid,
                           @PathParam("type")String type,@PathParam("title")String title,
                           @PathParam("info")String info,@PathParam("authority")String authority){

        Result result = new Result();


        if(!StringUtils.checkKey(id)||!StringUtils.checkKey(userid)||
                !StringUtils.checkKey(type)||!StringUtils.checkKey(title)||
                !StringUtils.checkKey(info)||!StringUtils.checkKey(authority)){
            result.setNullFalse();
            return result;
        }

        try {
            //检测是否有此用户
            Optional<CustomEntity> res = customDao.findById(userid);
            if(res==null||!res.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }

            PostsEntity postsEntity = new PostsEntity();

            //数据初始化
            postsEntity.setId(DateUtil.getIdFromDate());
            postsEntity.setUserid(userid);
            postsEntity.setTitle(title);
            postsEntity.setType(type);
            postsEntity.setInfo(info);
            postsEntity.setAuthority(authority);
            postsEntity.setCreattime(DateUtil.getTime());
            postsEntity.setUpdatetime(DateUtil.getTime());
            postsEntity.setOperator(userid);
            //保存到数据库
            postsDao.save(postsEntity);

            result.setOK("发帖成功",postsEntity.getId());
            return  result;

        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    //回复帖子
    @PostMapping(value = "/replyPosts")
    public  Result replyPosts(@PathParam("postsid")String postsid,@PathParam("user")String user,@PathParam("info")String info){
            Result result = new Result();

            if(!StringUtils.checkKey(postsid)||!StringUtils.checkKey("user")||!StringUtils.checkKey("info")){
                result.setNullFalse();
                return  result;
            }

            try{

                Optional<PostsEntity> resposts = postsDao.findById(postsid);
                if(resposts== null||!resposts.isPresent()){}

                return result;
            }catch (Exception e){
                e.printStackTrace();
                result.setSysFalse();
                return result;
            }



    }

>>>>>>> 6341f4cf10ac85c7a924c7fe7c4c059487441200


}
