package com.chatfest.common.transport;

import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.RequestHeader;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class RequestCodec {
    public static Request deserialize(byte[] bytes) {
        byte type = bytes[1];
        String from = new String(bytes, 2, 8);
        String to = new String(bytes, 10, 8);
        long timestamp = longFrom8Bytes(bytes, 18);
        int length = intFrom4Bytes(bytes, 26);
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

    public static byte[] serialize(Request request) throws Exception {
        RequestHeader header = request.getHeader();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(RequestHeader.MAGIC);
        baos.write(header.getType());
        String from = header.getFrom();
        String to = header.getTo();
        baos.write(from.getBytes());
        for (int i = 0; i < 8 - from.length(); i++) {
            baos.write('\n');
        }
        baos.write(to.getBytes());
        for (int i = 0; i < 8 - to.length(); i++) {
            baos.write('\n');
        }
        baos.write(longTo8Bytes(header.getTimestamp()));
        baos.write(intTo4Bytes(request.getMessage().length()));
        baos.write(request.getMessage().getBytes());
        return baos.toByteArray();
    }

    private static byte[] intTo4Bytes(int v) {
        byte[] res = new byte[4];
        res[0] = (byte) (v >>> 24);
        res[1] = (byte) (v >>> 16);
        res[2] = (byte) (v >>> 8);
        res[3] = (byte) (v);
        return res;
    }

    private static byte[] longTo8Bytes(long v) {
        byte[] res = new byte[8];
        for (int i = 0; i < 8; i++) {
            res[i] = (byte) (v >>> ((7 - i) * 8));
        }
        return res;
    }

    private static int intFrom4Bytes(byte[] bytes, int offset) {
        int res = 0;
        for (int i = offset; i < offset + 4; i++) {
            res |= ((bytes[i] & 0xFF) << (3 - i) * 8);
        }
        return res;
    }

    private static long longFrom8Bytes(byte[] bytes, int offset) {
        long res = 0;
        for (int i = offset; i < offset + 8; i++) {
            res |= ((bytes[i] & 0xFFL) << (7 - i) * 8);
        }
        return res;
    }

    @Test
    public void test() {
        byte[] bytes = {0, 0, 0, 0, (byte) 0xEE, (byte) 0xEE, (byte) 0xEE, (byte) 0xEE};
        byte[] bytes1 = {0, (byte) 0xEE, (byte) 0xEE, (byte) 0xEE};
        Assert.assertEquals(15658734, intFrom4Bytes(bytes1, 0));
        Assert.assertEquals(4008636142L, longFrom8Bytes(bytes, 0));
    }
}