package com.forum.demo.Controller;


import com.forum.demo.Annotation.LogPointCut;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.*;
import com.forum.demo.Entity.*;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.RedisOperator;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@EnableTransactionManagement
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

    @Autowired
    ReplyDao replyDao;

    @Autowired
    FavoriteDao favoriteDao;

    @Autowired
    UserInfoDao userInfoDao;


    //获取帖子的列表
    //type：帖子的类型    pageNumber：页码,rule:排序规则：creattime,read,count
    //返回指定类型的帖子的数组
    @GetMapping(value = "/getPostsList")
    public Result getPostsList(@PathParam("type")String type,@PathParam("pageNumber")String pageNumber,@PathParam("rule")String rule){
        Result result = new Result();

        if(!StringUtils.checkKey(type)||!StringUtils.checkKey(pageNumber)||!StringUtils.checkKey(rule)){
            result.setNullFalse();
            return result;
        }

        try{
            int number = Integer.valueOf(pageNumber);
            if(number<0){
                result.setFalse(201,"页码错误");
                return result;
            }

            if(!rule.equals("creattime")&&!rule.equals("number")&&!rule.equals("readnum")){
                result.setFalse(201,"排列规则错误");
                return result;
            }

            System.out.println(rule);
            //前端返回的规则进行查询
            PageRequest page = PageRequest.of(number,15, Sort.Direction.DESC,rule);
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
            if(number<0){
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

        if(!StringUtils.checkKey(id)){
            result.setNullFalse();
            return result;
        }

        try {
            Optional<PostsEntity> res = postsDao.findById(id);
            if (!res.isPresent()) {
                result.setFalse(201, "无此帖子");
                return result;

            }
            PostsEntity postsEntity = res.get();
            int count = Integer.valueOf(postsEntity.getNumber()) + 1;
            System.out.println(postsEntity);
            String countstr = String.valueOf(count);
            postsEntity.setNumber(count);
            postsDao.save(postsEntity);

            result.setOK("获取成功", postsEntity);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    //发帖
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/newPosts")
    public Result newPosts(@PathParam("userid")String userid,
                           @PathParam("type")String type,@PathParam("title")String title,
                           @PathParam("info")String info,@PathParam("authority")String authority){

        Result result = new Result();


        if(!StringUtils.checkKey(userid)||
                !StringUtils.checkKey(type)||!StringUtils.checkKey(title)||
                !StringUtils.checkKey(info)||!StringUtils.checkKey(authority)){
            result.setNullFalse();
            return result;
        }

        try {
            //检测是否有此用户
            Optional<UserInfoEntity> res = userInfoDao.findById(userid);
            if(!res.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }

            PostsEntity postsEntity = new PostsEntity();

            String zero = "0";

            //数据初始化
            postsEntity.setId(DateUtil.getIdFromDate());
            postsEntity.setUserid(userid);
            postsEntity.setTitle(title);
            postsEntity.setType(type);
            postsEntity.setInfo(info);
            postsEntity.setReadnum(0);
            postsEntity.setNumber(0);
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
    @Transactional
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/replyPosts")
    public  Result replyPosts(@PathParam("postsid")String postsid,@PathParam("user")String user,
                              @PathParam("info")String info,@PathParam("top")String top){
            Result result = new Result();

            if(!StringUtils.checkKey(postsid)||!StringUtils.checkKey("user")||
                    !StringUtils.checkKey(info)||!StringUtils.checkKey(top)){
                result.setNullFalse();
                return  result;
            }

            try{

                Optional<PostsEntity> resposts = postsDao.findById(postsid);
                if(resposts== null||!resposts.isPresent()){
                    result.setFalse(201,"无此帖子");
                    return result;
                }



                Optional<UserInfoEntity> resCus = userInfoDao.findById(user);
                if(resCus==null||!resCus.isPresent()){
                    result.setFalse(201,"无此用户");
                    return result;
                }

                int topNum = Integer.valueOf(top);
                if(topNum<1){
                    result.setFalse(201,"楼层错误");
                    return  result;
                }

                PostsEntity postsEntity  = resposts.get();
                int num = postsEntity.getReadnum() + 1;
                postsEntity.setReadnum(num);
                postsDao.save(postsEntity);

                ReplyEntity replyEntity = new ReplyEntity();
                replyEntity.setId(DateUtil.getIdFromDate());
                replyEntity.setPostsid(postsid);
                replyEntity.setUser(user);
                replyEntity.setInfo(info);
                replyEntity.setCreattime(DateUtil.getTime());
                replyEntity.setUpdatetime(DateUtil.getTime());
                replyEntity.setOperator(user);
                replyEntity.setTop(topNum);
                replyDao.save(replyEntity);
                result.setOK("回复成功",replyEntity.getId());
                return result;
            }catch (Exception e){
                e.printStackTrace();
                result.setSysFalse();
                return result;
            }



    }


    //删除帖子
    @Transactional
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/removePosts")
    public Result removePosts(@PathParam("postsid")String postsid,@PathParam("userid")String userid){
        Result result = new Result();

        if(!StringUtils.checkKey(postsid)||!StringUtils.checkKey(userid)){
            result.setNullFalse();
            return result;
        }

        try {

            Optional<PostsEntity> respo = postsDao.findById(postsid);
            if(respo == null||!respo.isPresent()){
                result.setFalse(201,"无此帖子");
                return  result;
            }

            PostsEntity postsEntity = respo.get();
            if(!postsEntity.getUserid().equals(userid)){
                result.setFalse(201,"无此权限");
                return  result;
            }

            postsDao.delete(postsEntity);
            replyDao.deleteByPostsidEquals(postsid);

            result.setOK("删除成功",true);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    //修改帖子内容
    @MonitorRequest
    @LogPointCut
    @PostMapping(value = "/updatePosts")
    public Result updatePosts(@PathParam("postsid")String postsid,@PathParam("userid")String userid,
                              @PathParam("info")String info,@PathParam("title")String title,
                              @PathParam("authority")String authority,@PathParam("type")String type){

        Result result = new Result();

        if(!StringUtils.checkKey(postsid)||!StringUtils.checkKey(userid)||
                !StringUtils.checkKey(type)||!StringUtils.checkKey(title)||
                !StringUtils.checkKey(info)||!StringUtils.checkKey(authority)){
            result.setNullFalse();
            return result;
        }

        try {

            Optional<PostsEntity> respo = postsDao.findById(postsid);
            if(respo==null||!respo.isPresent()){
                result.setFalse(201,"无此帖子");
                return result;
            }

            PostsEntity postsEntity = respo.get();
            if(!postsEntity.getUserid().equals(userid)){
                result.setFalse(201,"无此权限");
                return result;
            }

            postsEntity.setTitle(title);
            postsEntity.setType(type);
            postsEntity.setInfo(info);
            postsEntity.setOperator(userid);
            postsEntity.setUpdatetime(DateUtil.getTime());
            postsEntity.setAuthority(authority);

            postsDao.save(postsEntity);
            result.setOK("更新成功",true);
            return result;

        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return  result;
        }



    }

    //获取回复消息
    @GetMapping(value = "/getReply")
    public Result getReply(@PathParam("postsid")String postsid,@PathParam("pageNum")String pageNum){
        Result result = new Result();

        if(!StringUtils.checkKey(postsid)||!StringUtils.checkKey(pageNum)){
            result.setNullFalse();
            return result;
        }


        try {

            int number = Integer.valueOf(pageNum);
            if(number<0){
                result.setFalse(201,"页码错误");
                return result;
            }

            Optional<PostsEntity> respo = postsDao.findById(postsid);
            if(respo==null||!respo.isPresent()){
                result.setFalse(201,"无此帖子");
                return result;
            }

            PageRequest page = PageRequest.of(number,15, Sort.Direction.ASC,"creattime");
            List<ReplyEntity> list = new ArrayList<>();
            Page<ReplyEntity> reslist = replyDao.findAllByPostsidIn(postsid,page);
            if(reslist.hasContent()){
                list = reslist.getContent();
            }

            result.setOK("获取成功",list);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }


    }


    //搜索帖子
    //value:搜索字  pageNum：页码
    //返回搜索记录
    @LogPointCut
    @GetMapping(value = "/getSearch")
    public Result getSearch(@PathParam("value")String value,@PathParam("pageNum")String pageNum){
        Result result = new Result();

        if(!StringUtils.checkKey(value)||!StringUtils.checkKey(pageNum)){
            result.setNullFalse();
            return result;
        }

        try{
            int num = Integer.valueOf(pageNum);
            if(num<0){
                result.setFalse(201,"页码错误");
                return result;
            }

            PageRequest page = PageRequest.of(num,10, Sort.Direction.DESC,"creattime");

            List<PostsEntity> list = postsDao.findAllByTitleIn(value,page);

            if(list==null){
                result.setFalse(201,"无搜索结果");
                return result;
            }

            result.setOK("搜索成功",list);
            return result;


        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }


    //收藏帖子
    //帖子id，用户id
    @PostMapping(value = "/favoritePosts")
    @MonitorRequest
    @LogPointCut
    public Result favoritePosts(@PathParam("userid")String userid,@PathParam("postsid")String postsid){
        Result result  = new Result();

        if(!StringUtils.checkKey(userid)||!StringUtils.checkKey(postsid)){
            result.setNullFalse();
            return result;
        }

        try{
            Optional<UserInfoEntity> usercheck = userInfoDao.findById(userid);
            if(!usercheck.isPresent()){
                result.setFalse(201,"无此用户");
                return result;
            }

            Optional<PostsEntity> postscheck = postsDao.findById(postsid);
            if(!postscheck.isPresent()){
                result.setFalse(201,"无此帖子");
                return result;
            }

            FavoriteEntity favoriteEntity = new FavoriteEntity();
            favoriteEntity.setId(DateUtil.getIdFromDate());
            favoriteEntity.setUserid(userid);
            favoriteEntity.setPostslist(postsid);
            favoriteEntity.setCreatetime(DateUtil.getTime());
            favoriteEntity.setUpdatetime(DateUtil.getTime());
            favoriteEntity.setOperator("000000");
            favoriteDao.save(favoriteEntity);

            result.setOK("收藏成功",favoriteEntity.getId());
            return result;


        }
        catch (Exception e){
            result.setSysFalse();
            e.printStackTrace();
            return result;
        }


    }


    //取消收藏
    @PostMapping(value = "/deleteFavorite")
    @MonitorRequest
    @Transactional
    @LogPointCut
    public Result deleteFavorite(@PathParam("userid")String userid,@PathParam("favoriteid")String favoriteid){
        Result result  = new Result();

        if(!StringUtils.checkKey(userid)||!StringUtils.checkKey(favoriteid)){
            result.setNullFalse();
            return result;
        }

        try{
            Optional<UserInfoEntity> usercheck = userInfoDao.findById(userid);
            if(!usercheck.isPresent()){
                result.setFalse(201,"无此用户");
                return result;
            }

            Optional<FavoriteEntity> favoriteCheck = favoriteDao.findById(favoriteid);
            if(!favoriteCheck.isPresent()){
                result.setFalse(201,"您未收藏");
                return result;
            }

            FavoriteEntity favoriteEntity = favoriteCheck.get();
            if(!favoriteEntity.getUserid().equals(userid)){
                result.setFalse(201,"无此权限");
                return result;
            }

            favoriteDao.delete(favoriteEntity);
            result.setOK("删除成功",true);
            return  result;


        }
        catch (Exception e){
            result.setSysFalse();
            e.printStackTrace();
            return result;
        }


    }


}





