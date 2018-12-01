package com.chatfest.server.Handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler {

    private SelectionKey key;
    private ServerSocketChannel serverSocketChannel;

    public AcceptHandler(SelectionKey key) {
        this.key = key;
        this.serverSocketChannel = (ServerSocketChannel) key.channel();
    }

    public SocketChannel handle() {
        try {
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(key.selector(), SelectionKey.OP_READ);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
