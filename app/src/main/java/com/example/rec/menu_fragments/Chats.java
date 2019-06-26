package com.example.rec.menu_fragments;

public class Chats {
    public String sender;
    public String receiver;
    public String message;

    public Chats()
    {

    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Chats(String sender, String receiver, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = msg;
    }
}
