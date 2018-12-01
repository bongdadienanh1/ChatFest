package com.chatfest.common.transport;

import com.chatfest.common.domain.User;
import com.chatfest.common.types.MsgType;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private static final long serialVersionUID = -2821427368528745569L;

    private User from;
    private User to;
    private MsgType type;
    private Date date;
    private byte[] body;

    public Message() {
    }

    public Message(User from, User to, MsgType type, Date date, byte[] body) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.date = date;
        this.body = body;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
