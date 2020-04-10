package com.zhaodong.websocket.service;

public interface WsUserService {
    /**
     * 根据获取token获取用户code
     * @param token
     * @return
     */
    String getUserCodeByToken(String token);
}
