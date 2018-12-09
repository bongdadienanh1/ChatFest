package com.chatfest.common.util;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class TransportUtil {

    public static int getBodyLength(byte[] bytes, int offset) {
        return TransportUtil.intFrom4Bytes(bytes, offset);
    }

    public static byte[] intTo4Bytes(int v) {
        byte[] res = new byte[4];
        res[0] = (byte) (v >>> 24);
        res[1] = (byte) (v >>> 16);
        res[2] = (byte) (v >>> 8);
        res[3] = (byte) (v);
        return res;
    }

    public static byte[] longTo8Bytes(long v) {
        byte[] res = new byte[8];
        for (int i = 0; i < 8; i++) {
            res[i] = (byte) (v >>> ((7 - i) * 8));
        }
        return res;
    }

    public static int intFrom4Bytes(byte[] bytes, int offset) {
        int res = 0;
        for (int i = offset; i < offset + 4; i++) {
            res |= ((bytes[i] & 0xFF) << (3 - i + offset) * 8);
        }
        return res;
    }

    public static long longFrom8Bytes(byte[] bytes, int offset) {
        long res = 0;
        for (int i = offset; i < offset + 8; i++) {
            res |= ((bytes[i] & 0xFFL) << (7 - i + offset) * 8);
        }
        return res;
    }

    public static String getUserName(byte[] bytes, int offset) {
        int nameLength = 0;
        for (int i = offset; i < offset + 8; i++) {
            nameLength++;
            if (bytes[i] == '\n') {
                return new String(bytes, offset, nameLength - 1);
            }
        }
        return new String(bytes, offset, nameLength);
    }
}
