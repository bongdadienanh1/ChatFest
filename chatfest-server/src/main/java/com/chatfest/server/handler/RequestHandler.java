package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;

import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class RequestHandler {

    Request request;
    protected SelectionKey key;
    static AtomicInteger onlineUsers = new AtomicInteger(0);

    public RequestHandler() {
    }

    public RequestHandler(Request request, SelectionKey key) {
        this.request = request;
        this.key = key;
    }

    public abstract void handle();
}
