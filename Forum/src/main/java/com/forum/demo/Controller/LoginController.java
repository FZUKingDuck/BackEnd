package com.forum.demo.Controller;

import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.UserInfoDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.Entity.UserInfoEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.File;
import java.sql.Date;
import java.util.Optional;


@RestController
@EnableTransactionManagement
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    CustomDao customDao;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    UserInfoDao userInfoDao;


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
            redisOperator.set("loginUser:"+request.getRemoteHost(), customerEntity.getId(),1000*60*60);

            UserInfoEntity userInfoEntity =  userInfoDao.findUserInfoEntityByCustomid(customerEntity.getId());
            if(userInfoEntity==null){
                result.setSysFalse();
                return result;
            }


            result.setOK("登录成功",userInfoEntity.getId());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    @Transactional
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
            customEntity.setPower("2");
            customEntity.setOperator("000000");
            customEntity.setCreattime(DateUtil.getTime());
            customEntity.setUpdatetime(DateUtil.getTime());

            customDao.save(customEntity);

            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setId(DateUtil.getIdFromDate());
            userInfoEntity.setCity("未知");
            userInfoEntity.setSignature("无");
            userInfoEntity.setOperator("000000");
            userInfoEntity.setSex(0);
            userInfoEntity.setName(customEntity.getName());
            userInfoEntity.setCustomid(customEntity.getId());
            userInfoEntity.setBirth(DateUtil.getTime());
            userInfoEntity.setCreattime(DateUtil.getTime());
            userInfoEntity.setUpdatetime(DateUtil.getTime());

            userInfoDao.save(userInfoEntity);

            String pathHead = "/root/web/image/";
            String oldurl = pathHead + "templete.jpg";
            File old  = new File(oldurl);
            File file = new File(pathHead+userInfoEntity.getId()+".jpg");
            FileUtils.copyFile(old,file);

            result.setOK("注册成功",userInfoEntity.getId());

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }


    //给管理员用 的重置密码的接口
    @PostMapping("/resetPwd")
    @LogPointCut
    @MonitorRequest
    public Result resetPwd(@PathParam("name")String name,@PathParam("pwd")String pwd){
        Result result = new Result();

        if(!StringUtils.checkKey(name)){
            result.setNullFalse();
            return result;
        }

        if(!pwd.equals("147258369")){
            result.setFalse(201,"无此权限");
        }

        try{
            CustomEntity customEntity = customDao.findCustomEntityByName(name);
            if(customEntity==null){
                result.setFalse(201,"无此用户");
                return result;
            }

            customEntity.setPassword("123456");
            result.setOK("重置成功",customEntity.getId());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }


    }

    @GetMapping("/isAdmin")
        public Result isAdmin(@PathParam("name")String name){
            Result result = new Result();

            if(!StringUtils.checkKey(name)){
                result.setNullFalse();
                return result;
            }

            try{
                CustomEntity customEntity = customDao.findCustomEntityByName(name);
                if(customEntity==null){
                    result.setFalse(201,"无此用户");
                    return result;
                }

                if(customEntity.getPower().equals("0")){
                    result.setOK("是管理员",true);
                }

                else
                    result.setOK("不是管理员",false);
                return result;

            }catch (Exception e){
                result.setSysFalse();
                return result;
            }

    }

    @GetMapping("/isTeacher")
    public Result isTeacher(@PathParam("name")String name){
        Result result = new Result();

        if(!StringUtils.checkKey(name)){
            result.setNullFalse();
            return result;
        }

        try{
            CustomEntity customEntity = customDao.findCustomEntityByName(name);
            if(customEntity==null){
                result.setFalse(201,"无此用户");
                return result;
            }

            if(customEntity.getPower().equals("1")){
                result.setOK("是教师",true);
            }

            else
                result.setOK("不是教师",false);
            return result;

        }catch (Exception e){
            result.setSysFalse();
            return result;
        }

    }

}
