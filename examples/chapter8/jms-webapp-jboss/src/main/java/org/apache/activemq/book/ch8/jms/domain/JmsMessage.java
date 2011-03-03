package org.apache.activemq.book.ch8.jms.domain;

public class JmsMessage {

    private String replyTo; 
    private int timeToLive;
    private boolean persistent; 
    private String messagePayload;
    
    public JmsMessage() {}
    
    public String getReplyTo() {
        return replyTo;
    }
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }
    public int getTimeToLive() {
        return timeToLive;
    }
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
    public boolean isPersistent() {
        return persistent;
    }
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }
    public String getMessagePayload() {
        return messagePayload;
    }
    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }
    
}
