package com.forum.demo.Controller;


import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.PostsDao;
import com.forum.demo.DAO.TaskDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

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
    @GetMapping(value = "getPostsList")
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
            PageRequest page = PageRequest.of(number,10, Sort.Direction.DESC,"creattime");
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
   // @GetMapping(value = "getPostsListAll")


}
