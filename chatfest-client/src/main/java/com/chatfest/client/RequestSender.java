package com.chatfest.client;

import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.RequestCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RequestSender implements Runnable {

    private Request request;
    private SocketChannel channel;

    public RequestSender() {
    }

    public RequestSender(Request request, SocketChannel channel) {
        this.request = request;
        this.channel = channel;
    }

    @Override
    public void run() {
        byte[] bytes = RequestCodec.serialize(request);
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        // write() 非阻塞
        while (buf.hasRemaining()) {
            try {
                channel.write(buf);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}