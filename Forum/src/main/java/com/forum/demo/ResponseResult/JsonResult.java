package com.forum.demo.ResponseResult;

public class JsonResult {
   static public Result fail(){
       Result result = new Result();
       result.setFalse(205,"用户未登录");
        return result;
   }

}
