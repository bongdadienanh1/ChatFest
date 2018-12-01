package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.transport.Message;

import java.nio.channels.SelectionKey;

public abstract class MessageHandler {

    protected Message message;
    protected SelectionKey key;

    public MessageHandler() {
    }

    public MessageHandler(Message message, SelectionKey key) {
        this.message = message;
        this.key = key;
    }

    abstract void handle();
}
