package com.chatfest.server.handler;

import com.chatfest.common.transport.Response;
import com.chatfest.common.transport.ResponseCodec;
import com.chatfest.common.transport.ResponseHeader;
import com.chatfest.common.types.ResponseType;
import com.chatfest.server.manager.UserManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class SystemMsgHandler {

//    private static final String PREFIX = "[PROMPT]: ";

    private SelectionKey key;

    public SystemMsgHandler(SelectionKey key) {
        this.key = key;
    }

    public void single(String message, byte type) {
        single(message, "SYSTEM", type);
    }
    public void single(String message, String from, byte type) {
        ResponseHeader header = ResponseHeader.build()
                .type(type)
                .from(from)
                .contentLength(message.length());
        Response response = Response.build()
                .responseHeader(header)
                .message(message);
        send(response, key);
    }

    public void broadcast(String message, byte type, SelectionKey key) {
        broadcast(message, "SYSTEM", type, key);
    }

    public void broadcast(String message, String from, byte type, SelectionKey key) {
        Collection<SelectionKey> keys = UserManager.getKeys();
        Iterator<SelectionKey> it = keys.iterator();
        ResponseHeader header = ResponseHeader.build()
                .type(type)
                .from(from)
                .contentLength(message.length());
        Response response = Response.build()
                .responseHeader(header)
                .message(message);
        while (it.hasNext()) {
            SelectionKey to = it.next();
            if (to != key) {
                send(response, to);
            }
        }
    }

    private void send(Response response, SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        try {
            ByteBuffer buf = ByteBuffer.wrap(ResponseCodec.serialize(response));
            while (buf.hasRemaining()) {
                client.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
