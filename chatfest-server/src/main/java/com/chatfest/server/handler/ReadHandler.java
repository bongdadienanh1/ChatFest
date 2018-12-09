package com.chatfest.server.handler;

import com.chatfest.common.transport.Properties;
import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.RequestCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

public class ReadHandler implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ReadHandler.class);
    private SelectionKey key;
    private SocketChannel socketChannel;

    public ReadHandler(SelectionKey key) {
        this.key = key;
        this.socketChannel = (SocketChannel) key.channel();
    }

    @Override
    public void run() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer header = ByteBuffer.allocate(Properties.REQUEST_HEADER_LENGTH.getVal());
        try {
            while (header.hasRemaining()) {
                socketChannel.read(header);
            }
            baos.write(header.array());
            int bodyLength = RequestCodec.getBodyLength(header.array());
            ByteBuffer body = ByteBuffer.allocate(bodyLength);
            while (body.hasRemaining()) {
                socketChannel.read(body);
            }
            body.flip();
            baos.write(body.array());
            byte[] bytes = baos.toByteArray();
            baos.close();
            if (bytes != null && bytes.length != 0) {
                Request request = RequestCodec.deserialize(bytes);
                RequestHandler handler = RequestHandlerFactory.getMessageHandler(request, key);
                if (handler == null) {
                    logger.error("bad request from {}", socketChannel);
                } else {
                    handler.handle();
                }
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
