package com.forum.demo.Controller;

import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.sql.Date;


@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    CustomDao customDao;

    @Autowired
    RedisOperator redisOperator;


    //登录接口
    //name:账号,pwd:md5加密过的密码

    @LogPointCut
    @PostMapping(value = "/login")
    public Result login(HttpServletRequest request, @PathParam("name")String name, @PathParam("pwd")String pwd){
        Result result = new Result();

        if(!StringUtils.checkKey(name)||!StringUtils.checkKey(pwd)){
            result.setNullFalse();
            return result;
        }
        try{
            CustomEntity customerEntity = customDao.findCustomEntityByName(name);
            if(null == customerEntity){
                result.setFalse(201,"无此用户");
                return result;
            }

            //验证密码是否正确
            if(!pwd.equals(customerEntity.getPassword())){
                result.setFalse(201,"密码错误");
            }

            //将数据加入缓存池
            redisOperator.set("loginUser:"+request.getRemoteHost(), DateUtil.getIdFromDate(),1000*60*60);

            result.setOK("登录成功",true);

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }


    @LogPointCut
    @PostMapping(value = "/register")
    public Result register(@PathParam("name")String name,@PathParam("pwd")String pwd){
        Result result = new Result();

        //参数验证
        if(!StringUtils.checkKey(name)||!StringUtils.checkKey(pwd)){
            result.setNullFalse();
            return result;

        }

        try {
            //数据检测
            CustomEntity customEntity = customDao.findCustomEntityByName(name);
            if(customEntity!=null){
                result.setFalse(201,"用户已存在，请直接登录");
                return result;
            }

            customEntity = new CustomEntity();
            customEntity.setId(DateUtil.getIdFromDate());
            customEntity.setName(name);
            customEntity.setPassword(pwd);
            customEntity.setOperator("000000");
            customEntity.setCreattime(DateUtil.getTime());
            customEntity.setUpdatetime(DateUtil.getTime());

            customDao.save(customEntity);

            result.setOK("注册成功",true);

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }

}
