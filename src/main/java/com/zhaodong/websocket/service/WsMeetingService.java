package com.zhaodong.websocket.service;

import java.io.IOException;

/**
 * 会议websocket service
 * @author zhaodong
 * @date 2019/12/13
 */
public interface WsMeetingService {
    /**
     * 群发消息发送消息给用户
     * @param meetingCode 会议code
     * @param message 消息
     * @throws IOException
     */
    public void sendToUsers(String meetingCode, String message);

    public void sendSingleUser(String userCode,String message);


}
