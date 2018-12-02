package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;

import java.nio.channels.SelectionKey;

public class SingleRequestHandler extends RequestHandler {
    public SingleRequestHandler() {
    }

    public SingleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {

    }
}
