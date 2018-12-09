package com.chatfest.common.transport;

import com.chatfest.common.util.TransportUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseCodec {

    public static int getBodyLength(byte[] bytes) {
        return TransportUtil.intFrom4Bytes(bytes, 18);
    }

    public static byte[] serialize(Response response) {
        ResponseHeader header = response.getHeader();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(ResponseHeader.MAGIC);
        baos.write(header.getType());
        String from = header.getFrom();
        try {
            baos.write(from.getBytes());
            for (int i = 0; i < 8 - from.length() ; i++) {
                baos.write('\n');
            }
            baos.write(TransportUtil.longTo8Bytes(header.getTimestamp()));
            baos.write(TransportUtil.intTo4Bytes(header.getContentLength()));
            baos.write(response.getMessage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Response deserialize(byte[] bytes) {
        byte type = bytes[1];
        String from = TransportUtil.getUserName(bytes, 2);
        long timestamp = TransportUtil.longFrom8Bytes(bytes, 10);
        int length = TransportUtil.intFrom4Bytes(bytes, 18);
        String message = new String(bytes, 22, length);
        ResponseHeader header = ResponseHeader.build()
                .type(type)
                .from(from)
                .timestamp(timestamp)
                .contentLength(length);
        return Response.build()
                .responseHeader(header)
                .message(message);
    }
}
