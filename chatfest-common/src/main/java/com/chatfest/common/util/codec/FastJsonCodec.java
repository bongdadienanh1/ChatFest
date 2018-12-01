package com.chatfest.common.util.codec;

import com.alibaba.fastjson.JSON;

public class FastJsonCodec {

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    public static byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

}
