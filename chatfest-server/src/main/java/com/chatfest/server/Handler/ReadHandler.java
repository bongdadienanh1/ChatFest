package com.chatfest.server.Handler;

import com.chatfest.common.transport.Message;
import com.chatfest.common.util.codec.FastJsonCodec;
import com.chatfest.server.Handler.MsgHandler.MessageHandlerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadHandler implements Runnable {

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
                Message message = FastJsonCodec.deserialize(bytes, Message.class);
                MessageHandlerFactory.getMessageHandler(message, key);
            }
            // 重新加上READ标记
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
