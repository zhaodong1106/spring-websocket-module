package com.zhaodong.websocket.handler;

import com.zhaodong.websocket.service.WsMeetingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket连接处理器
 * @author zhaodong
 * @date 2019/12/13
 */
@Component
public class MyHandler extends TextWebSocketHandler {

    public  static final Map<String, WebSocketSession> users=new ConcurrentHashMap<>();

    public  static final String WS_MEETING_TO_USER_PREFIX="WS_MEETING_TO_USER:";

    private static final Logger LOG=LoggerFactory.getLogger(MyHandler.class);

    @Autowired
    private WsMeetingStore wsMeetingStore;

    /**
     * 建立连接后回调
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(LOG.isDebugEnabled()) {
            LOG.debug("成功建立websocket连接!");
        }
        String userCode = (String) session.getAttributes().get("userCode");
        String meetingCode = (String) session.getAttributes().get("meetingCode");
        wsMeetingStore.addUserCode(meetingCode, userCode);
//        if(result==1){
//            redisTemplate.expire(WS_MEETING_TO_USER_PREFIX + meetingCode,2,TimeUnit.DAYS);
//        }
        users.put(userCode,session);
    }

    /**
     * 处理接收到的消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userCode = (String) session.getAttributes().get("userCode");
        String meetingCode = (String) session.getAttributes().get("meetingCode");
        if(LOG.isDebugEnabled()) {
            LOG.debug("收到消息:" + message.getPayload());
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
//    public void sendMessageToUser(String userId, TextMessage message) throws IOException {
//        for (String id : users.keySet()) {
//            if (id.equals(userId)) {
//                if (users.get(id).isOpen()) {
//                    users.get(id).sendMessage(message);
//                }
//                break;
//            }
//        }
//    }

    /**
     * 关闭连接后回调
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(LOG.isDebugEnabled()) {
            LOG.debug("断开了连接了userCode:{}", session.getAttributes().get("userCode"));
        }
        String meetingCode = (String) session.getAttributes().get("meetingCode");
        String userCode = (String) session.getAttributes().get("userCode");
        users.remove(userCode);
        wsMeetingStore.removeUserCode(meetingCode,userCode);
    }
}
