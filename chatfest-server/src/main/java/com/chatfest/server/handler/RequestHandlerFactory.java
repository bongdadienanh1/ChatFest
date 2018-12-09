package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

import java.nio.channels.SelectionKey;

public class RequestHandlerFactory {

    public static RequestHandler getMessageHandler(Request request, SelectionKey key) {
        byte type = request.getHeader().getType();
        if (type == RequestType.LOGIN.getCode()) {
            return new LoginRequestHandler(request, key);
        } else if (type == RequestType.LOGOUT.getCode()) {
            return new LogoutRequestHandler(request, key);
        } else if (type == RequestType.SINGLE.getCode()) {
            return new SingleRequestHandler(request, key);
        } else if (type == RequestType.MULTIPLE.getCode()) {
            return new MultipleRequestHandler(request, key);
        } else {
            return null;
        }
    }
}
