package com.chatfest.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static Map<String, String> accounts = new HashMap<>();

    // TODO: 后期会使用数据库
    static {
        for (int i = 1; i <= 10; i++) {
            accounts.put("user" + i, "password" + i);
        }
    }

    public static boolean checkLogin(String userName, String password) {
        String dbPassword = accounts.get(userName);
        return dbPassword != null && dbPassword.equals(password);
    }

}
