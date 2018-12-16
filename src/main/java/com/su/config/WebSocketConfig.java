package com.su.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *  WebSocket的相关配置
 */
@Component
public class WebSocketConfig {

    /**
     *  注入一个ServerEndpointExporter的Bean到spring容器中
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
