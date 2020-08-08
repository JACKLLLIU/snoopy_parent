package com.bilibili.snoopy.exceptionhandler;

import com.bilibili.commonUtils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定什么异常的出现执行这个方法
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultMessage error(Exception e){
        e.printStackTrace();
        return  ResultMessage.error().message("全局异常处理");
    }

    //特定异常处理
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public ResultMessage error(SQLException e){
        e.printStackTrace();
        return  ResultMessage.error().message("sql异常");
    }

    //自定义异常处理
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public ResultMessage error(MyException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return  ResultMessage.error().code(e.getCode()).message(e.getMsg());

    }
}
