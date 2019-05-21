package com.forum.demo.Controller;

import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomerDao;
import com.forum.demo.Entity.CustomerEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class LoginController {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    RedisOperator redisOperator;

    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/login")
    public Result login(@PathParam("name")String name,@PathParam("pwd")String pwd){
        Result result = new Result();

        if(!StringUtils.checkKey(name)||!StringUtils.checkKey(pwd)){
            result.setNullFalse();
            return result;
        }
        try{
            CustomerEntity customerEntity = customerDao.findCustomerEntityByName(name);
            if(null == customerEntity){
                result.setFalse(201,"无此用户");
                return result;
            }

            //验证密码是否正确
            if(!pwd.equals(customerEntity.getPassword())){
                result.setFalse(201,"密码错误");
            }

            redisOperator.set("loginUser:"+customerEntity.getName(),customerEntity.getId(),1000*60);

            result.setOK("登录成功",true);

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }

}
