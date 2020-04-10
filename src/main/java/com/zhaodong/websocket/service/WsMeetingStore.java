package com.zhaodong.websocket.service;

import java.util.Set;

public interface WsMeetingStore {
    /**
     * 获取会议下所以进入的观众
     * @param meetingCode
     * @return
     */
    Set<String> getUserCodesByMeetingCode(String meetingCode);

    /**
     * 增加会议下的单个观众
     * @param meetingCode
     */
    void  addUserCode(String meetingCode,String userCode);


    /**
     * 移除会议下的单个观众
     * @param meetingCode
     */
    void  removeUserCode(String meetingCode,String userCode);

    /**
     * 移除会议下的所有观众
     * @param meetingCode
     */
    void  removeMeeting(String meetingCode);

    /**
     * 移除所有会议
     */
    void removeAllMeetings();

}
