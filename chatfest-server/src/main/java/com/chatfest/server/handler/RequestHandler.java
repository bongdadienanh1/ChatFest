package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;

import java.nio.channels.SelectionKey;

public abstract class RequestHandler {

    protected Request request;
    protected SelectionKey key;

    public RequestHandler() {
    }

    public RequestHandler(Request request, SelectionKey key) {
        this.request = request;
        this.key = key;
    }

    public abstract void handle();
}
