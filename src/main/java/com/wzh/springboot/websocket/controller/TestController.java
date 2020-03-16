package com.wzh.springboot.websocket.controller;

import com.wzh.springboot.websocket.business.BusinessException;
import com.wzh.springboot.websocket.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzh
 * @date 2020/3/7 - 13:38
 */
@RestController
public class TestController {

    @GetMapping("/test/{id}")
    public String test(@PathVariable String id){
        if(id.equals("1")){
            throw new BusinessException("id为1");
        }
        return "ok";
    }

    @PostMapping(value = "/json",produces = "application/json;charset=utf-8")
    public String testJson(MessageDto messageDto){
        return "ok"+messageDto.getFromId();
    }
}
