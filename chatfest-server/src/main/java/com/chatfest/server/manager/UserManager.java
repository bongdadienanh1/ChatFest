package com.chatfest.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static Map<String, String> accounts = new HashMap<>();
    private static Map<String, SocketChannel> onlineUsers = new HashMap<>();

    // TODO: 后期再使用数据库
    static {
        for (int i = 1; i <= 10; i++) {
            accounts.put("user" + i, "password" + i);
        }
    }

    public static boolean checkLogin(String userName, String password) {
        String dbPassword = accounts.get(userName);
        return dbPassword != null && dbPassword.equals(password);
    }

    /**
     * @param username 目标username
     * @return 返回username对应的SocketChannel，若该用户不在线，返回null
     */
    public static SocketChannel getChannel(String username) {
        return onlineUsers.get(username);
    }

    public boolean login() {
        // TODO
        return false;
    }

    public boolean logout() {
        // TODO
        return false;
    }
}
