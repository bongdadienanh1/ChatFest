package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.transport.Message;

import java.nio.channels.SelectionKey;

public class SingleMessageHandler extends MessageHandler {
    public SingleMessageHandler() {
    }

    public SingleMessageHandler(Message message, SelectionKey key) {
        super(message, key);
    }

    @Override
    void handle() {

    }
}
