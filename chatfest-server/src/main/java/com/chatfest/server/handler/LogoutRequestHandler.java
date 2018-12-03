package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseStatus;
import com.chatfest.common.util.codec.FastJsonCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LogoutRequestHandler extends RequestHandler {

    public LogoutRequestHandler() {
    }

    public LogoutRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        SocketChannel client = (SocketChannel) key.channel();
        String username = request.getFrom();
        onlineUsers.decrementAndGet();
        Response response = Response.build()
                .responseStatus(ResponseStatus.LOGOUT_SUCCESS)
                .body(null)
                .from("SYSTEM");
        try {
            client.write(ByteBuffer.wrap(FastJsonCodec.serialize(response)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = "\"" + username + "\" has logged out!";
        new SystemMsgHandler(key).broadcast(message);
    }
}
