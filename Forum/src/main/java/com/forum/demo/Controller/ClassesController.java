package com.forum.demo.Controller;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.*;
import com.forum.demo.Entity.*;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.StringUtils;
import org.jboss.jandex.ClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.websocket.server.PathParam;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/class")
public class ClassesController {
    @Autowired
    ClassInfoDao classInfoDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ClassMemberDao classMemberDao;

    @Autowired
    ClassTaskDao classTaskDao;

    @Autowired
    CustomDao customDao;

    @GetMapping(value = "/getClassInfo")
    public Result getClassInfo(@PathParam("id")String id){
        Result result = new Result() ;
        ClassInfoEntity classInfoEntity=new ClassInfoEntity();

        if(!StringUtils.checkKey(id))
        {
            result.setNullFalse();
            return result;
        }

        try{
            Optional<ClassInfoEntity> cla =   classInfoDao.findAllById(id);
            if(cla==null||!cla.isPresent()){
                result.setFalse(201,"无此班级");
            }
            classInfoEntity= cla.get();
            //classInfoEntity.getTeacher();
            result.setOK("班级查找成功",classInfoEntity);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    @GetMapping(value = "/getClassTask")
    public Result getClassTask(@PathParam("classid")String classid,@PathParam("pageNumber")String pageNumber,@PathParam("type")String type){
        Result result = new Result();

        if(!StringUtils.checkKey(classid)||!StringUtils.checkKey(pageNumber)||!StringUtils.checkKey(type)){
            result.setNullFalse();
            return result;
        }

        try {
            Optional<ClassInfoEntity> classInfoEntity = classInfoDao.findAllById(classid);
            if(!classInfoEntity.isPresent()){
                result.setFalse(201,"无此班级");
                return result;
            }

            int num = Integer.valueOf(pageNumber);
            if(num<0){
                result.setFalse(201,"页码错误");
                return result;
            }


            PageRequest page = PageRequest.of(num,10, Sort.Direction.DESC,"createtime");
            List<ClassTaskEntity> classTaskEntity = classTaskDao.findAllByClassid(classid,page);
            result.setOK("查找成功",classTaskEntity);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }


    }

    @PostMapping(value = "/setClassTask")
    public  Result setClassTask(@PathParam("classid")String classid,@PathParam("name")String name,
                                @PathParam("info")String info,@PathParam("endtime")String endtime,@PathParam("userid")String userid){
        Result result = new Result();

        if(!StringUtils.checkKey(classid)||!StringUtils.checkKey(name)||
                !StringUtils.checkKey(info)||!StringUtils.checkKey(userid)||
                !StringUtils.checkKey(endtime)){
            result.setNullFalse();
            return result;
        }

        try {

            Optional<ClassInfoEntity> classcheck = classInfoDao.findById(classid);
            if(!classcheck.isPresent()){
                result.setFalse(201,"无此班级");
                return result;
            }

            ClassInfoEntity classInfoEntity = classcheck.get();

            if(!classInfoEntity.getTeacher().equals(userid)){
                result.setFalse(201,"无此权限");
                return result;
            }

            ClassTaskEntity classTaskEntity = new ClassTaskEntity();

            classTaskEntity.setId(DateUtil.getIdFromDate());
            classTaskEntity.setClassid(classid);
            classTaskEntity.setName(name);
            classTaskEntity.setInfo(info);
            classTaskEntity.setType("0");
            classTaskEntity.setOperator(userid);
            classTaskEntity.setCreatetime(DateUtil.getTime());
            classTaskEntity.setUpdatetime(DateUtil.getTime());
            classTaskEntity.setEndtime(DateUtil.changeTime(endtime));

            classTaskDao.save(classTaskEntity);

            result.setOK("新增成功",classTaskEntity.getId());
            return result;


        }
        catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }


    @MonitorRequest
    @RequestMapping(value = "/addStudent")
    public Result addStudent(@PathParam("classid")String classid,@PathParam("user")String user) {
        ClassMemberEntity classMemberEntity = new ClassMemberEntity();

        Result result = new Result();

        if (!StringUtils.checkKey(user) || !StringUtils.checkKey(classid) || !StringUtils.checkKey(user)) {
            result.setNullFalse();
            return result;
        }
        try {
            Optional<UserInfoEntity> cus = userInfoDao.findById(user);
            if (cus == null || !cus.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }

            classMemberEntity.setId(DateUtil.getIdFromDate());  //时间戳生成id
            classMemberEntity.setClassid(classid);
            classMemberEntity.setUser(user);
            classMemberEntity.setOperator(user);
            classMemberEntity.setCreattime(DateUtil.getTime());
            classMemberEntity.setUpdatetime(DateUtil.getTime());
            classMemberEntity.setStatus(0);

            classMemberDao.save(classMemberEntity);
            result.setOK("添加成功", classMemberEntity.getId());
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }


    @MonitorRequest
    @PostMapping(value = "/deleteClassMember")
    public Result deleteClassMember(@PathParam("id")String id)
    {
        Result result = new Result();

        if(!StringUtils.checkKey(id)){
            result.setNullFalse();
            return result;
        }

        try{
            ClassMemberEntity classMemberEntity = classMemberDao.findClassMemberEntityByUser(id);
            if(classMemberEntity == null){
                result.setFalse(201,"无此学生");
                return  result;
            }

            classMemberDao.delete(classMemberEntity);
            result.setOK("删除成功",true);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

    @MonitorRequest
    @RequestMapping(value = "/getStudentList")

    public Result getStudentList(@PathParam("id")String id,@PathParam("pageNumber")String pageNumber){//班级id 索引学生列表
        Result result = new Result();
        if(!StringUtils.checkKey(id))
        {
            result.setNullFalse();
            return result;
        }

        try{
            Optional<ClassInfoEntity> cla = classInfoDao.findById(id);
            if(cla==null||!cla.isPresent()){
                result.setFalse(201,"无此班级");
            }
            int number=Integer.valueOf(pageNumber);
            PageRequest page=PageRequest.of(number,10, Sort.Direction.DESC,"creattime");
            List<ClassMemberEntity> classMemberPage=classMemberDao.findClassMemberEntitiesByClassid(id);
            List<UserInfoEntity> list = new ArrayList<>();
            for (ClassMemberEntity classMemberEntity:classMemberPage
                 ) {
                    Optional<UserInfoEntity> user = userInfoDao.findById(classMemberEntity.getUser());
                    if(user.isPresent())
                        list.add(user.get());
            }

            result.setOK("班级成员查找成功",list);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    @PostMapping(value = "/getClassInfoByUser")
    public Result getClassId(@PathParam("userid")String userid){
        Result result = new Result();
        if (!StringUtils.checkKey(userid)){
            result.setNullFalse();
            return result;
        }

        try {
            ClassMemberEntity classMemberEntity = classMemberDao.findClassMemberEntityByUser(userid);
            if(classMemberEntity == null){
                result.setFalse(201,"无此学生");
                return result;
            }

            String classid = classMemberEntity.getClassid();

            Optional<ClassInfoEntity> classInfo = classInfoDao.findById(classid);
            if(!classInfo.isPresent()){
                result.setSysFalse();
                return result;
            }

            ClassInfoEntity classInfoEntity = classInfo.get();
            result.setOK("获取成功",classInfoEntity);
            return result;


        }catch (Exception e){
            result.setSysFalse();
            e.printStackTrace();
            return result;
        }

    }

}
