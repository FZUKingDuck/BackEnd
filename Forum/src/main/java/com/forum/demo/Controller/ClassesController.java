package com.forum.demo.Controller;
import com.forum.demo.DAO.*;
import com.forum.demo.Entity.*;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.ClassTaskDao;
import com.forum.demo.DAO.ClassMemberDao;

import com.forum.demo.UtilTool.DateUtil;
import org.springframework.data.domain.Sort;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/class")
public class ClassesController {
    @Autowired
    ClassInfoDao classInfoDao;

    @Autowired
    ClassMemberDao classMemberDao;

    @Autowired
    CustomDao customDao;

    @Autowired
    ClassTaskDao classTaskDao;
    @MonitorRequest
    @RequestMapping(value = "/getClassInfo")
    public Result getClassInfo(@PathParam("id")String id){
        Result result = new Result() ;
        ClassInfoEntity classInfoEntity=new ClassInfoEntity();

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

    @MonitorRequest
    @RequestMapping(value = "/getStudentList")

    public Result getStudentList(@PathParam("id")String id,@PathParam("pageNumber")String pageNumber){//班级id 索引学生列表
        Result result = new Result();
        if(!StringUtils.checkKey(id))
        {
            result.setNullFalse();
            return result;
        }

        ClassMemberEntity classMemberEntity = new ClassMemberEntity();
        try{
            Optional<ClassMemberEntity> cla = classMemberDao.findAllByClassid(id);
            if(cla==null||!cla.isPresent()){
                result.setFalse(201,"无此班级");
            }
            int number=Integer.valueOf(pageNumber);
            PageRequest page=PageRequest.of(number,15, Sort.Direction.DESC,"creattime");
            List<ClassMemberEntity> classMemberPage=classMemberDao.findClassMemberEntitiesByClassid(id);
            result.setOK("班级成员查找成功",classMemberPage);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
     }

    //根据班级id以及任务类型返回任务列表
    @MonitorRequest
    @RequestMapping(value = "/classTask")

    public Result getClassTask(@PathParam("id")String id,@PathParam("pageNumber")String pageNumber,@PathParam("type"
    )String type){
        Result result = new Result() ;
        ClassInfoEntity classInfoEntity=new ClassInfoEntity();

        if(!StringUtils.checkKey(id)&&!StringUtils.checkKey(type))
        {
            result.setNullFalse();
            return result;
        }

        try{
            Optional<ClassInfoEntity> cla =     classInfoDao.findById(id);
            if(cla==null||!cla.isPresent()){
                result.setFalse(201,"无此班级");
            }
            int number=Integer.valueOf(pageNumber);
            PageRequest page =PageRequest.of(number,15, Sort.Direction.DESC,"creattime");
            List<ClassTaskEntity> classpage=classTaskDao.findClassTaskEntitiesByClassidAndType(id,type,page);//(id,page);


            //classInfoEntity.getTeacher();
            result.setOK("任务查找成功",classpage);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }

    @MonitorRequest
    @RequestMapping(value = "/addStudent")

    public Result addStudent(@PathParam("classid")String classid,@PathParam("user")String user,
                             @PathParam("operator")String operator,@PathParam("Remark")String Remark) {
        ClassMemberEntity classMemberEntity = new ClassMemberEntity();
        classMemberEntity.setId(DateUtil.getIdFromDate());  //时间戳生成id
        Result result = new Result();

        if (!StringUtils.checkKey(user) || !StringUtils.checkKey(classid) || !StringUtils.checkKey(operator)
                || !StringUtils.checkKey(Remark)) {
            result.setNullFalse();
            return result;
        }
        try {
            Optional<CustomEntity> cus = customDao.findById(user);
            if (cus == null || !cus.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }
            classMemberEntity.setId(classid);
            classMemberEntity.setUser(user);
            classMemberEntity.setOperator(operator);
            classMemberEntity.setRemark(Remark);

            classMemberDao.save(classMemberEntity);
            result.setOK("添加成功", classMemberEntity);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }
    @MonitorRequest
    @RequestMapping(value = "/deleteClassMember")

    public Result deleteClassMember(@PathParam("id")String id)
    {
        Result result = new Result();

        if(!StringUtils.checkKey(id)){
            result.setNullFalse();
            return result;
        }

        try{
            Optional<ClassMemberEntity> member =classMemberDao.findById(id);
            if(member == null||!member.isPresent()){
                result.setFalse(201,"无此学生");
                return  result;
            }
            ClassMemberEntity classMemberEntity = member.get();
            classMemberDao.delete(classMemberEntity);
            result.setOK("删除成功",true);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }

}
