package com.forum.demo.Controller;

import com.forum.demo.ResponseResult.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test")
    public Result test(){
        Result result = new Result();
        result.setOK("is ok",true);
        return result;
    }



}
