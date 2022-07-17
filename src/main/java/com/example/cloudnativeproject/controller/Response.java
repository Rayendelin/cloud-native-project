package com.example.cloudnativeproject.controller;

public class Response {

    private String code;
    private String msg;
    private Object result;


    public Response(String code, String msg, Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static Response success(Object result){
        return new Response("200","Success",result);
    }

    public static Response success(){
        return new Response("200","Success","操作成功");
    }

    public static Response fail(String code,String msg){
        return new Response(code,msg,null);
    }

}
