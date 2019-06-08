package com.forum.demo.Controller;
import com.forum.demo.DAO.*;
import com.forum.demo.Entity.ClassInfoEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
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

    public Result getClassInfo(@PathParam("id")String id){
        Result result = new Result() ;
        ClassInfoEntity classInfoEntity=new ClassInfoEntity();

        if(!StringUtils.checkKey(id))
        {
            result.setNullFalse();
            return result;
        }

        try{
            Optional<ClassInfoEntity> cla =     classInfoDao.findAllById(id);
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
    /*
    public Result getStudentList(@PathParam("id")String id){//班级id 索引学生列表
        Result result = new Result();
        if(!StringUtils.checkKey(id))
        {
            result.setNullFalse();
            return result;
        }

        PageRequest page = PageRequest.of()
    }
    */
    public Result getClassTask()
}
