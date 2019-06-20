package com.forum.demo.Controller;

import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.PostsDao;
import com.forum.demo.DAO.ReplyDao;
import com.forum.demo.DAO.UserInfoDao;
import com.forum.demo.Entity.ImageInfoEntity;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.Entity.ReplyEntity;
import com.forum.demo.Entity.UserInfoEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/user")
@EnableTransactionManagement
@RestController
public class UserInfoController {


    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    PostsDao postsDao;

    @Autowired
    ReplyDao replyDao;

    //根据用户的 id获取用户的信息
    //需要做验证
    @LogPointCut
    //这个是验证登录的
    @MonitorRequest
    @PostMapping(value = "/getUserInfo")
    public Result getUserInfo(@PathParam("id")String id){
        Result result = new Result();

        //检查参数
        if(!StringUtils.checkKey(id)){
            result.setNullFalse();
            return result;
        }

        //检查是否有用户
        try{
            Optional<UserInfoEntity> userCheck = userInfoDao.findById(id);
            if(!userCheck.isPresent()){
                result.setFalse(201,"无此用户");
                return result;
            }
            UserInfoEntity userInfoEntity = userCheck.get();
            userInfoEntity.setOperator("");
            result.setOK("获取成功",userInfoEntity);
            return result;


        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }


    }


    //修改用户信息
    @MonitorRequest
    @LogPointCut
    @PostMapping("/updateUserInfo")
    public  Result updateUserInfo(@PathParam("userid")String userid,@PathParam("name")String name,
                                  @PathParam("sex")String sex,@PathParam("birth")String birth,
                                  @PathParam("signature")String signature,@PathParam("city")String city) {
        Result result = new Result();

        if (!StringUtils.checkKey(userid) || !StringUtils.checkKey(name)
                || !StringUtils.checkKey(sex) || !StringUtils.checkKey(birth)
                || !StringUtils.checkKey(signature)) {
            result.setNullFalse();
            return result;
        }

        try {

            //查找是否有此用户
            Optional<UserInfoEntity> userCheck = userInfoDao.findById(userid);
            if(!userCheck.isPresent()){
                result.setFalse(201,"无此用户");
                return  result;
            }

            UserInfoEntity userInfoEntity = userCheck.get();

            userInfoEntity.setName(name);
            userInfoEntity.setSex(sex.equals("1")?1:0);
            userInfoEntity.setBirth(DateUtil.changeTime(birth));
            userInfoEntity.setSignature(signature);
            userInfoEntity.setCity(city);
            userInfoEntity.setOperator(userid);
            userInfoDao.save(userInfoEntity);

             result.setOK("修改成功",userInfoEntity.getId());
             return result;
            } catch (Exception e) {
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    //根据id获取用户的回复消息
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/getReply")
    public Result getReply(@PathParam("userid")String userid,@PathParam("pageNum")String pageNum){
        Result result = new Result();

         if(!StringUtils.checkKey(userid)||!StringUtils.checkKey(pageNum)){
             result.setNullFalse();
             return result;
         }

         try{

             Optional<UserInfoEntity> userCheck = userInfoDao.findById(userid) ;
             if(!userCheck.isPresent()){
                 result.setFalse(201,"无此用户");
             }

             int num = Integer.valueOf(pageNum);
             if(num<0){
                 result.setFalse(201,"页码错误");
                 return  result;
             }

             //分页查找
             PageRequest page = PageRequest.of(num,20, Sort.Direction.DESC,"updatetime");

             //获取数据
             Page<ReplyEntity> list = replyDao.findAllByUserIn(userid,page);
             result.setOK("获取成功",list);
             return  result;

         }
         catch (Exception e){
             e.printStackTrace();
             result.setSysFalse();
             return result;
         }

    }

    //根据id获取所有发的帖子
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/getPosts")
    public Result getPosts(@PathParam("usrid")String userid,@PathParam("pageNum")String pageNum){
        Result result = new Result();

        if(!StringUtils.checkKey(userid)||!StringUtils.checkKey(pageNum)){
            result.setNullFalse();
            return result;
        }

        try{

            Optional<UserInfoEntity> userCheck = userInfoDao.findById(userid) ;
            if(!userCheck.isPresent()){
                result.setFalse(201,"无此用户");
            }

            int num = Integer.valueOf(pageNum);
            if(num<0){
                result.setFalse(201,"页码错误");
                return  result;
            }

            //分页查找
            PageRequest page = PageRequest.of(num,20, Sort.Direction.DESC,"updatetime");

            //获取数据
            List<PostsEntity> list = postsDao.findAllByUseridIn(userid,page);
            result.setOK("获取成功",list);
            return  result;

        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }


    //返回用户头像
    @GetMapping(value = "/getImage" , produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathParam("id")String id) {
        if (!StringUtils.checkKey(id)) {
            return null;
        }
        String pathHead = "/root/web/image/";
        try {
            //获取数据库中的数据
            Optional<UserInfoEntity> result = userInfoDao.findById(id);
            if (result == null || !result.isPresent()) {
                return null;
            }


            String url = pathHead + id + ".jpg";

            File image = new File(url);
            FileInputStream inputStream = new FileInputStream(image);

            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
