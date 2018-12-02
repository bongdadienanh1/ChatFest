package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

import java.nio.channels.SelectionKey;

public class RequestHandlerFactory {

    public static RequestHandler getMessageHandler(Request request, SelectionKey key) {
        RequestType type = request.getType();
        switch (type.getCode()) {
            case 1:
                return new LoginRequestHandler(request, key);
            case 2:
                return new LogoutRequestHandler(request, key);
            case 3:
                return new SingleRequestHandler(request, key);
            case 4:
                return new MultipleRequestHandler(request, key);
            default:
                return null;
        }
    }
}
