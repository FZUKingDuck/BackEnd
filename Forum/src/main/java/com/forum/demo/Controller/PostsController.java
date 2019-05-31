package com.forum.demo.Controller;


import com.forum.demo.DAO.CustomDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value =  "/posts")
public class PostsController {

    @Autowired
    CustomDao customDao;

    @Autowired
    RedisOperator redisOperator;





}
