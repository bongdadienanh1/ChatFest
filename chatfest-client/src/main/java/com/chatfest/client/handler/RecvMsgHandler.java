package com.chatfest.client.handler;

import com.chatfest.client.util.MessagePrinter;
import com.chatfest.common.transport.Response;

import java.nio.channels.SelectionKey;

public class RecvMsgHandler extends ResponseHandler {
    public RecvMsgHandler(Response response) {
        super(response);
    }

    @Override
    public void handle() {
        String message = response.getMessage();
        String from = response.getHeader().getFrom();
        MessagePrinter.print(message, from);
    }
}
