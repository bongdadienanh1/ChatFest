package com.chatfest.common.transport;

import com.chatfest.common.types.ResponseStatus;

import java.io.Serializable;
import java.util.Date;

public class Response implements Serializable {

    private static final long serialVersionUID = 7624285367892945735L;

    private String from;
    private ResponseStatus status;
    private Date date;
    private byte[] body;

    private Response() {

    }

    private Response(Date date) {
        this.date = date;
    }

    public Response(String from, ResponseStatus status, Date date, byte[] body) {
        this.from = from;
        this.status = status;
        this.date = date;
        this.body = body;
    }

    public static Response build() {
        return new Response(new Date());
    }

    public Response responseStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    public Response body(byte[] body) {
        this.body = body;

        return this;
    }

    public Response from(String from) {
        this.from = from;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
