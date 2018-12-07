package com.chatfest.server.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler {

    private SelectionKey key;

    public AcceptHandler(SelectionKey key) {
        this.key = key;
    }

    public SocketChannel handle() {
        try {
            SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
            client.configureBlocking(false);
            client.register(key.selector(), SelectionKey.OP_READ);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
