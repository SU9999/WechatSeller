package com.su.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  用于消息推送的WebSocket
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Logger log = LoggerFactory.getLogger(WebSocket.class);
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数={}",webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数={}",webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到消息，message={}", message);
    }


    /**
     * 广播消息
     * @param message
     */
    public void sendMessage(String message){
        for (WebSocket webSocket : webSocketSet){
            log.info("【websocket消息】广播消息");
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("【websocket消息】广播消息发生异常，e={}", e);
            }
        }
    }
}
