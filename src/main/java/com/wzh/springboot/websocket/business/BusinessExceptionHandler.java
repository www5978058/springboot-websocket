package com.wzh.springboot.websocket.business;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wzh
 * @date 2020/3/7 - 13:41
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public String handlerBusinessException(BusinessException ex){
        return "handlerException: "+ex.getMessage();
    }
}
