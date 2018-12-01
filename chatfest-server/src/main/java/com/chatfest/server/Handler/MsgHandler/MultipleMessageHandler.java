package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.transport.Message;

import java.nio.channels.SelectionKey;

public class MultipleMessageHandler extends MessageHandler {

    public MultipleMessageHandler() {
    }

    public MultipleMessageHandler(Message message, SelectionKey key) {
        super(message, key);
    }

    @Override
    void handle() {

    }
}
