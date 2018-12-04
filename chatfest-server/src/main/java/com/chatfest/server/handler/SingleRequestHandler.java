package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseStatus;
import com.chatfest.common.util.codec.KryoCodec;
import com.chatfest.server.manager.UserManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class SingleRequestHandler extends RequestHandler {
    public SingleRequestHandler() {
    }

    public SingleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        String from = request.getFrom();
        String to = request.getTo();
        byte[] body = request.getBody();
        Date date = request.getDate();

        SocketChannel rcver = (SocketChannel) UserManager.getKey(to).channel();
        if (rcver == null) {
            String msg = "\"" + to + "\" if offline.";
            new SystemMsgHandler(key).single(msg, ResponseStatus.SEND_MSG_FAIL);
        } else {
            new SystemMsgHandler(key).single("send msg success!", ResponseStatus.SEND_MSG_SUCCESS);
            String msg = new String(body);
            Response response = Response.build()
                    .from(from)
                    .responseStatus(ResponseStatus.RCV_MSG)
                    .body(msg.getBytes());
            try {
                rcver.write(ByteBuffer.wrap(KryoCodec.serialize(response)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
