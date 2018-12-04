package com.chatfest.server.handler;

import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseStatus;
import com.chatfest.common.util.codec.KryoCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class SystemMsgHandler {

//    private static final String PREFIX = "[PROMPT]: ";

    private SelectionKey key;

    public SystemMsgHandler() {
    }

    public SystemMsgHandler(SelectionKey key) {
        this.key = key;
    }

    public void single(String message, ResponseStatus status) {
        Response response = Response.build()
                .responseStatus(status)
                .from("SYSTEM")
                .body(message.getBytes(StandardCharsets.UTF_8));
        SocketChannel client = (SocketChannel) key.channel();
        try {
            client.write(ByteBuffer.wrap(KryoCodec.serialize(response)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        broadcast(message, "SYSTEM");
    }

    public void broadcast(String message, String from) {
        Set<SelectionKey> keys = key.selector().keys();
        Iterator<SelectionKey> it = keys.iterator();
        Response response = Response.build()
                .responseStatus(ResponseStatus.SYSTEM_PROMPT)
                .from(from)
                .body(message.getBytes(StandardCharsets.UTF_8));
        while (it.hasNext()) {
            SelectionKey key = it.next();
            SocketChannel client = (SocketChannel) key.channel();
            try {
                client.write(ByteBuffer.wrap(KryoCodec.serialize(response)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
