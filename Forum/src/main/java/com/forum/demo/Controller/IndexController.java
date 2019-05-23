package com.forum.demo.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forum.demo.DAO.PostsDao;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/index")
public class IndexController {
    @Autowired
    PostsDao postsDao;


    //获取热门帖子
    @JsonIgnore
    @GetMapping(value = "/getPostsList")
    public Result getPostsList(){
        Result result = new Result();

        try {
            Sort sort = new Sort(Sort.Direction.DESC,"remark");
            List<PostsEntity> list = postsDao.findAll(sort);
            list = list.subList(0,10);
            for (PostsEntity p :list) {
                p.setUserid("");
            }
            result.setOK("获取成功",list);

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }



    @GetMapping(value = "/add")
    public void add(){
        PostsEntity postsEntity = new PostsEntity();
        postsEntity.setId(DateUtil.getIdFromDate());
        postsEntity.setUserid("15586219479869");
        postsEntity.setAuthority("1");
        postsEntity.setTitle("测试1");
        postsEntity.setInfo("这是一个测试的数据");
        postsEntity.setCreattime(DateUtil.getTime());
        postsEntity.setUpdatetime(DateUtil.getTime());
        postsEntity.setOperator("15586219479869");
        postsEntity.setType("html");
        postsDao.save(postsEntity);
    }

    @GetMapping(value = "/getImageUrl")
    public Result getImageUrl(){
        Result result = new Result();

        List<String > list = new ArrayList<>();
        list.add("/page1.jpg");
        list.add("/page2.jpg");
        result.setOK("",list);
        return result;

    }



}
