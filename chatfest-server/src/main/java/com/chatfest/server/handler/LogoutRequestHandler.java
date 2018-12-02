package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;

import java.nio.channels.SelectionKey;

public class LogoutRequestHandler extends RequestHandler {

    public LogoutRequestHandler() {
    }

    public LogoutRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {

    }
}
