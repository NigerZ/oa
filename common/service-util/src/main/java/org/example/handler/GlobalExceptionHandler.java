package org.example.handler;

import org.example.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(OAException.class)
    @ResponseBody
    public Result error(OAException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }

    @ExceptionHandler(FileUploadException.class)
    @ResponseBody
    public Result error(FileUploadException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result error(LoginException e) {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }
}
