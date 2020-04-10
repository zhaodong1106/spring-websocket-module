package com.zhaodong.websocket.service.impl;

import com.zhaodong.websocket.handler.MyHandler;
import com.zhaodong.websocket.service.WsMeetingService;
import com.zhaodong.websocket.service.WsMeetingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaodong
 * @date 2019/12/13
 */
@Service
public class WsMeetingServiceImpl implements WsMeetingService {
    private Logger logger= LoggerFactory.getLogger(WsMeetingServiceImpl.class);

    @Autowired
    private WsMeetingStore wsMeetingStore;
    @Value("${ws.corePoolSize:5}")
    private int corePoolSize;
    @Value("${ws.maximumPoolSize:30}")
    private int maximumPoolSize;
    @Value("${ws.keepAliveTime:60}")
    private int keepAliveTime;
    /**
     * 处理消息线程池
     */
    private ThreadPoolExecutor executor;

    @PostConstruct
    public void init(){
        executor=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
    }
    @Override
    public void sendToUsers(String meetingCode,String message)  {
        executor.execute(()->{
            //查询redis中的案号代码下的用户code集合
            Set<String> userCodes = wsMeetingStore.getUserCodesByMeetingCode(meetingCode);
            for(String userCode:userCodes) {
                WebSocketSession webSocketSession = MyHandler.users.get(userCode);
                if(webSocketSession!=null) {
                    try {
                        //发送消息
                        webSocketSession.sendMessage(new TextMessage(message.getBytes()));
                    } catch (IOException e) {
                        logger.error("发送消息失败,消息是{}",message);
                    }
                }
            }
        });
    }

    @Override
    public void sendSingleUser(String userCode, String message) {
        executor.execute(()->{
            WebSocketSession webSocketSession = MyHandler.users.get(userCode);
            if(webSocketSession!=null) {
                try {
                    //发送消息
                    webSocketSession.sendMessage(new TextMessage(message.getBytes()));
                } catch (IOException e) {
                    logger.error("发送消息失败,消息是{}",message);
                }
            }
        });
    }
}
