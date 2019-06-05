package com.forum.demo.Controller;

import com.forum.demo.DAO.TaskDao;
import com.forum.demo.DAO.TaskListDao;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.Entity.TaskEntity;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.StringUtils;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.websocket.server.PathParam;
import java.util.List;

public class TaskController {
    @Autowired
    TaskDao taskDao;

    @Autowired
    TaskListDao taskListDao;
    //type：分类类型    pageNumber：页码
  public Result getTaskList(@PathParam("type")String type,@PathParam("pagenumber")String pageNumber){
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

          PageRequest page = PageRequest.of(0,15, Sort.Direction.DESC,"remark");
          if(null ==page){
              result.setFalse(201,"类型错误");
              return result;
          }

          List<TaskEntity> list= taskDao.findAllByTypeIn(type,page)
          result.setOK("查找成功",page);
      }catch (Exception e){
          e.printStackTrace();
          result.setSysFalse();
          return result;
      }
  }

  public Result publishTask()
}
