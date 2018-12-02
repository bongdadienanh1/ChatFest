package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.util.codec.FastJsonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadHandler implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ReadHandler.class);

    private SelectionKey key;
    private SocketChannel socketChannel;
    private ByteBuffer buffer;
    private byte[] bytes;

    public ReadHandler(SelectionKey key) {
        this.key = key;
        this.socketChannel = (SocketChannel) key.channel();
        buffer = ByteBuffer.allocate(1024);
    }

    @Override
    public void run() {
        try {
            while (socketChannel.read(buffer) >= 0) {
                buffer.flip();
                bytes = buffer.array();
                buffer.clear();
            }
            if (bytes != null && bytes.length != 0) {
                Request request = FastJsonCodec.deserialize(bytes, Request.class);
                RequestHandler handler = RequestHandlerFactory.getMessageHandler(request, key);
                if (handler == null) {
                    logger.error("bad request from {}", socketChannel);
                } else {
                    handler.handle();
                }
            }
            // 重新加上READ标记
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
