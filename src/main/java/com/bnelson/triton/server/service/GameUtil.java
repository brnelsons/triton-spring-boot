package com.bnelson.triton.server.service;

import com.bnelson.triton.server.pojo.ServerInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class GameUtil {
    public static boolean isLocalProcess(ServerInfo serverInfo) {
        Preconditions.checkNotNull(serverInfo);
        return (Strings.isNullOrEmpty(serverInfo.getAddress())
                || serverInfo.getAddress().contains("localhost")
                || serverInfo.getAddress().contains("127.0.0.1"))
                && !Strings.isNullOrEmpty(serverInfo.getLocalProcessName());
    }
}
