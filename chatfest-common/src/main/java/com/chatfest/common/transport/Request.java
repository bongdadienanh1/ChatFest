package com.chatfest.common.transport;

import com.chatfest.common.types.RequestType;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private static final long serialVersionUID = -2821427368528745569L;

    private Long length;
    private String from;
    private String to;
    private RequestType type;
    private Date date;
    private byte[] body;

    public Request() {
    }

    public Request(Date date) {
        this.date = date;
    }

    public Request(Long length, String from, String to, RequestType type, Date date, byte[] body) {
        this.length = length;
        this.from = from;
        this.to = to;
        this.type = type;
        this.date = date;
        this.body = body;
    }

    public static Request build() {
        return new Request(new Date());
    }

    public Request from(String from) {
        this.from = from;
        return this;
    }

    public Request to(String to) {
        this.to = to;
        return this;
    }

    public Request type(RequestType type) {
        this.type = type;
        return this;
    }

    public Request date(Date date) {
        this.date = date;
        return this;
    }

    public Request body(byte[] body) {
        this.body = body;
        return this;
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
