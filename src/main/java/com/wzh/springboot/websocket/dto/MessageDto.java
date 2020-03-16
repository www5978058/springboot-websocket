package com.wzh.springboot.websocket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wzh
 * @date 2020/3/7 - 16:36
 */
@Data
public class MessageDto implements Serializable {
    private static final long serialVersionUID = 3468352004150968551L;
    private String fromId;
    private String toId;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
