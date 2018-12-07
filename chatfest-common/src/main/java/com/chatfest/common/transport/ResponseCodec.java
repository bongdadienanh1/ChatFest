package com.chatfest.common.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseCodec {
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
            baos.write(RequestCodec.longTo8Bytes(header.getTimestamp()));
            baos.write(RequestCodec.intTo4Bytes(header.getContentLength()));
            baos.write(response.getMessage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Response deserialize(byte[] bytes) {
        byte type = bytes[1];
        String from = new String(bytes, 2, 8);
        long timestamp = RequestCodec.longFrom8Bytes(bytes, 10);
        int length = RequestCodec.intFrom4Bytes(bytes, 18);
        String message = new String(bytes, 19, length);
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
