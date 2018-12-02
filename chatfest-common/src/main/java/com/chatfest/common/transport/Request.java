package com.chatfest.common.transport;

import com.chatfest.common.types.RequestType;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private static final long serialVersionUID = -2821427368528745569L;

    private String from;
    private String to;
    private RequestType type;
    private Date date;
    private byte[] body;

    public Request() {
    }

    public Request(String from, String to, RequestType type, Date date, byte[] body) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.date = date;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
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
