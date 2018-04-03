package com.aliashik.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    private String message;
    private String messageDescription;

    public Message() {
    }

    public Message(String message, String messageDescription) {
        this.message = message;
        this.messageDescription = messageDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", messageDescription='" + messageDescription + '\'' +
                '}';
    }
}
