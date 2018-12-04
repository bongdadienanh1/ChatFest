package com.chatfest.client;

import com.chatfest.common.transport.Request;
import com.chatfest.common.util.codec.KryoCodec;

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
        try {
            channel.write(ByteBuffer.wrap(KryoCodec.serialize(request)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
