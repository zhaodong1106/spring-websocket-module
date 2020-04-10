package com.zhaodong.websocket.Message;

public enum MessageStatus {
    LIAN_MAI_APPLY("1001","连麦请求"),
    LIAN_MAIN_SUCCESS("1002","连麦请求同意");

    public String messageType;
    public String messageName;

    MessageStatus(String messageType, String messageName) {
        this.messageType = messageType;
        this.messageName = messageName;
    }
}
