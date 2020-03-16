package com.wzh.springboot.websocket.business;

/**
 * @author wzh
 * @date 2020/3/7 - 13:55
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
