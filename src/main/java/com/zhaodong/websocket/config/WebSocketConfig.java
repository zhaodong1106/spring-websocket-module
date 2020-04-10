package com.zhaodong.websocket.config;

import com.zhaodong.websocket.handler.MyHandler;
import com.zhaodong.websocket.handler.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author zhaodong
 * @date 2019/12/13
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;
    @Autowired
    private MyHandler myHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册长连接地址
        registry.addHandler(myHandler, "/ws/").addInterceptors(myInterceptor).setAllowedOrigins("*").withSockJS().setSupressCors(false);
    }

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }


}
