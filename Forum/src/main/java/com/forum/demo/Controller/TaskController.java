package com.forum.demo.Controller;

import com.forum.demo.Annotation.MonitorRequest;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.TaskDao;
import com.forum.demo.DAO.TaskListDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.Entity.TaskEntity;
import com.forum.demo.Entity.TaskListEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.StringUtils;
import com.sun.corba.se.spi.orb.StringPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/task")

public class TaskController {

    @Autowired
    CustomDao customDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    TaskListDao taskListDao;
    //type：分类类型    pageNumber：页码
  public Result getTaskList(@PathParam("sorttype")String type,@PathParam("pageNumber")String pageNumber){
      Result result = new Result();
      //pageNumber 页面  转换为number int  和Pageable page



      if(!StringUtils.checkKey(type)){
          result.setNullFalse();
          return result;
      }
      try {
          int number = Integer.valueOf(pageNumber);
          if(number<1){
              result.setFalse(201,"页码错误");
              return result;
          }

          PageRequest page = PageRequest.of(number,15, Sort.Direction.DESC,type);
          if(null ==page){
              result.setFalse(201,"类型错误");
              return result;
          }

          Page<TaskEntity> respage= taskDao.findAll(page);
          if (!respage.hasContent()){
              result.setSysFalse();
              return result;
          }

          List<TaskEntity>list = respage.getContent();

          result.setOK("查找成功",list);
          return result;
      }catch (Exception e){
          e.printStackTrace();
          result.setSysFalse();
          return result;
      }
  }

  @MonitorRequest
  @PostMapping(value = "/publishTask")

  //status:0 发布待审核 1 审核通过 2未通过审核
  //type:最新任务 热门任务 系统任务 付费任务
  public Result publishTask(@PathParam("id")String id,@PathParam("user")String user,
  @PathParam("type")String type,@PathParam("title")String title,@PathParam("status")String status
  ,@PathParam("money")String money,@PathParam("info")String info,@PathParam("endtime")String endtime
 ){


      TaskEntity taskEntity = new TaskEntity();
      taskEntity.setId(DateUtil.getIdFromDate());   //时间戳生成ID
      Result result=new Result();
      if(!StringUtils.checkKey(user)||!StringUtils.checkKey(type)||!StringUtils.checkKey(title)
              ||!StringUtils.checkKey(user)||!StringUtils.checkKey(status)||!StringUtils.checkKey(money)
              ||!StringUtils.checkKey(info)||!StringUtils.checkKey(endtime)){
          result.setNullFalse();
          return result;
      }
      try{
          Optional<CustomEntity> cus = customDao.findById(user);
          if(cus==null||!cus.isPresent())
          {
              result.setFalse(201,"无此用户");
              return result;
          }
          int sum =Integer.valueOf(money);
          if(sum<1){
              result.setFalse(201,"Money设置错误");
          }

          //CustomEntity customEntity = cus.get();//拿表记录
          taskEntity.setUser(user);
          taskEntity.setType(type);
          taskEntity.setTitle(title);
          taskEntity.setStatus(status);
          taskEntity.setStatus(status);
          taskEntity.setMoney(sum);
          taskEntity.setInfo(info);
          taskEntity.setEndtime(DateUtil.changeTime(endtime));

          taskDao.save(taskEntity);
          result.setOK("任务发布成功",taskEntity);
          return  result;
      }
      catch (Exception e){
          e.printStackTrace();
          result.setSysFalse();
          return result;
      }
  }
    @MonitorRequest
    @PostMapping(value = "/myTask")
    public Result myTask(@PathParam("user")String user, @PathParam("pageNumber")String pageNumber) {
        Result result = new Result();
        if (!StringUtils.checkKey(user)) {
            result.setNullFalse();
            return result;
        }
        try {
            Optional<CustomEntity> cus = customDao.findById(user);
            if (cus == null || !cus.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }
            int number = Integer.valueOf(pageNumber);
            if(number<1){
                result.setFalse(201,"页码错误");
                return result;
            }

            PageRequest page = PageRequest.of(number,15, Sort.Direction.DESC,"creattime");
            if(null ==page){
                result.setFalse(201,"类型错误");
                return result;
            }

            Page<TaskEntity> respage= taskDao.findAllByUser(user,page);
            if (!respage.hasContent()){
                result.setSysFalse();
                return result;
            }

            List<TaskEntity>list = respage.getContent();

            result.setOK("查找成功",list);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }
    }
    @MonitorRequest
    @PostMapping(value = "/undertakeTask")
    public  Result undertakeTask(@PathParam("user")String user,@PathParam("taskid")String taskid){
      Result result = new Result();

        if(!StringUtils.checkKey(user)||!StringUtils.checkKey(taskid)){
            result.setNullFalse();
            return result;
        }
        try {
            Optional<CustomEntity> cus = customDao.findById(user);
            if (cus == null || !cus.isPresent()) {
                result.setFalse(201, "无此用户");
                return result;
            }
            Optional<TaskEntity> idd = taskDao.findById(taskid);
            if (cus == null || !cus.isPresent()) {
                result.setFalse(201, "无此任务");
                return result;
            }

            TaskListEntity taskListEntity = taskListDao.findTaskListEntityByUserAndTaskid(user,taskid);
            if(taskListEntity!=null){
                result.setFalse(201,"已接取任务");
                return result;
            }
            Date date = new Date(System.currentTimeMillis());
            TaskListEntity taskListEntity1 = new TaskListEntity();
            taskListEntity1.setId(DateUtil.getIdFromDate()); //时间戳生成ID
            taskListEntity1.setUser(user);
            taskListEntity1.setTaskid(taskid);
            taskListEntity1.setStatus("0");
            taskListEntity1.setOperator(user);
            taskListEntity1.setCreattime(date);
            taskListEntity1.setUpdatetime(date);//这是什么？
            taskListDao.save(taskListEntity1);
            result.setOK("任务接取成功",taskListEntity1);
            return  result;
        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

    }
}
