package com.zhaodong.websocket.handler;


import com.zhaodong.websocket.service.WsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 握手拦截器
 * @author zhaodong
 * @date 2019/12/13
 */
@Component
public class MyInterceptor  implements HandshakeInterceptor {


    private static final Logger LOG=LoggerFactory.getLogger(MyInterceptor.class);

    @Autowired
    private WsUserService wsUserService;

    /**
     * 握手预处理
     * @param request
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if(LOG.isDebugEnabled()){
            LOG.debug("进入了握手预处理");
        }
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request ;
        String token = servletRequest.getServletRequest().getParameter("token");
        String meetingCode=servletRequest.getServletRequest().getParameter("meetingCode");
        if(LOG.isDebugEnabled()){
            LOG.debug("token:{}",token);
            LOG.debug("meetingCode:{}",meetingCode);
        }
        String userCode = wsUserService.getUserCodeByToken(token);
        if(meetingCode!=null&&userCode!=null){
            map.put("userCode",userCode);
            map.put("meetingCode",meetingCode);
            return true;
        }
        return false;
    }

    /**
     * 握手成功后
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        if(LOG.isDebugEnabled()){
            LOG.debug("握手完成了");
        }
    }
}
