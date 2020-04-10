package com.zhaodong.websocket.Message;

public class MessageText {
    private String messageType;
    private String messageName;
    private String messageContent;
    private String fromUserCode;
    private String fromUserName;

    public static MessageText ofMessageStatus(MessageStatus messageStatus,String messageContent,String fromUserCode,String fromUserName){
        return new MessageText(messageStatus.messageType,messageStatus.messageName,messageContent,fromUserCode,fromUserName);
    }

    public MessageText(String messageType, String messageName, String messageContent, String fromUserCode,String fromUserName) {
        this.messageType = messageType;
        this.messageName = messageName;
        this.messageContent = messageContent;
        this.fromUserCode = fromUserCode;
        this.fromUserName=fromUserName;
    }

    public MessageText() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(String fromUserCode) {
        this.fromUserCode = fromUserCode;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}
