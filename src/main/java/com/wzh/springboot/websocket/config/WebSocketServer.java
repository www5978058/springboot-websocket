package com.wzh.springboot.websocket.config;

import com.wzh.springboot.websocket.dto.MessageDto;
import com.wzh.springboot.websocket.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wzh
 * @date 2020/3/7 - 14:28
 */
@ServerEndpoint("/ws/{fromId}")
@Component
@Slf4j
public class WebSocketServer {
    /**
     * 静态变量，记录在线人数
     */
    private static volatile int onlineCount = 0;
    /**
     * 存放在线的用户的WebSocketServer
     */
    private static ConcurrentHashMap<String, WebSocketServer> onlineUserMap = new ConcurrentHashMap<>();
    /**
     * 与客户端的会话，通过它来传输
     */
    private Session session;
    /**
     * 用户id
     */
    private String fromId;

    /**
     * 建立连接后调用该方法-对应前端JS
     */
    @OnOpen
    public void onOpen(@PathParam("fromId") String fromId, Session session) {
        // 会话保存
        this.session = session;
        this.fromId = fromId;
        if (!onlineUserMap.containsKey(fromId)) {
            onlineUserMap.put(fromId, this);
            addOnlineCount();
        }
    }

    /**
     * 关闭连接时调用该方法-对应前端JS
     */
    @OnClose
    public void onClose() {
        if (onlineUserMap.containsKey(fromId)) {
            onlineUserMap.remove(fromId);
            subOnlineCount();
        }
    }

    /**
     * 接收客户端的message,根据是否有接收人选择进行广播还是指定发送
     */
    @OnMessage
    public void onMessage(String message) {
        /**
         * 1. 将message转化成对象，获取toId
         * 2. 通过toId去onlineUserMap获取WebSocketServer对象
         * 3. 调用sendMessage或sendAsyncMessage发送消息
         */
        try {
            MessageDto messageDto = MapperUtils.json2pojo(message, MessageDto.class);
            if (onlineUserMap.containsKey(messageDto.getToId())) {
                onlineUserMap.get(messageDto.getToId()).sendMessage(messageDto.getMessage());
            }else{
                this.sendMessage(messageDto.getToId()+"用户不在线");
            }
        } catch (Exception e) {
            log.info("message格式不对:{}",message);
        }
    }

    /**
     * 出现错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("fromId: {},ws错误,原因:{}", this.fromId, error.getMessage());
    }

    /**
     * 发送异步消息
     *
     * @param message
     */
    public void sendAsyncMessage(String message) {
        session.getAsyncRemote().sendText(message);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 统计在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数++
     */
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 在线人数--
     */
    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
