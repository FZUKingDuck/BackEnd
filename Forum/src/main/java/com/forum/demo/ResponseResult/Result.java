package com.forum.demo.ResponseResult;

public class Result<T> {

    //返回数据的统一格式
    //状态码
    private int code;
    //状态码说明
    private String message;
    //数据：用json格式返回的
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    //请求成功的统一处理函数
    public void setOK(String message, T data){
        this.message = message;
        this.code = 200;
        this.data = data;
    }
    //请求失败的统一处理函数
    public void setFalse(int code,String message){
        this.message = message;
        this.code = code;
        this.data = null;
    }

    public void setNullFalse(){
        this.message = "参数错误";
        this.code = 400;
        this.data = null;
    }

    public void setSysFalse(){
        this.message = "系统错误";
        this.code = 500;
        this.data = null;
    }

}