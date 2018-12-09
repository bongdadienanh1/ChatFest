package com.chatfest.common.transport;

import com.chatfest.common.util.TransportUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestCodec {

    public static int getBodyLength(byte[] bytes) {
        return TransportUtil.intFrom4Bytes(bytes, 26);
    }

    public static Request deserialize(byte[] bytes) {
        byte type = bytes[1];
        String from = TransportUtil.getUserName(bytes, 2);
        String to = TransportUtil.getUserName(bytes, 10);
        long timestamp = TransportUtil.longFrom8Bytes(bytes, 18);
        int length = TransportUtil.intFrom4Bytes(bytes, 26);
        String message = new String(bytes, 30, length);
        RequestHeader header = RequestHeader.build()
                .type(type)
                .from(from)
                .to(to)
                .timestamp(timestamp)
                .contentLength(length);
        return Request.build()
                .requestHeader(header)
                .message(message);
    }

    public static byte[] serialize(Request request) {
        RequestHeader header = request.getHeader();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(RequestHeader.MAGIC);
        baos.write(header.getType());
        String from = header.getFrom();
        String to = header.getTo();
        try {
            baos.write(from.getBytes());
            for (int i = 0; i < 8 - from.length(); i++) {
                baos.write('\n');
            }
            baos.write(to.getBytes());
            for (int i = 0; i < 8 - to.length(); i++) {
                baos.write('\n');
            }
            baos.write(TransportUtil.longTo8Bytes(header.getTimestamp()));
            baos.write(TransportUtil.intTo4Bytes(header.getContentLength()));
            baos.write(request.getMessage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}