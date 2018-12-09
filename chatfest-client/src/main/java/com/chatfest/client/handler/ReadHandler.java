package com.chatfest.client.handler;

import com.chatfest.common.transport.*;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadHandler implements Runnable {

    private SelectionKey key;

    public ReadHandler(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        SocketChannel client = (SocketChannel) key.channel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer header = ByteBuffer.allocate(Properties.RESPONSE_HEADER_LENGTH.getVal());
        try {
            while (header.hasRemaining()) {
                client.read(header);
            }
            baos.write(header.array());
            int bodyLength = ResponseCodec.getBodyLength(header.array());
            ByteBuffer body = ByteBuffer.allocate(bodyLength);
            while (body.hasRemaining()) {
                client.read(body);
            }
            body.flip();
            baos.write(body.array());
            byte[] bytes = baos.toByteArray();
            baos.close();
            if (bytes != null && bytes.length != 0) {
                Response response = ResponseCodec.deserialize(bytes);
                ResponseHandler handler = ResponseHandlerFactory.getResponseHandler(response);
                handler.handle();
            }
            // 重新加上READ标记
            if (key.isValid()) {
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                key.selector().wakeup();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
