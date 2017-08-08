package com.bnelson.triton.server.service;

import com.bnelson.triton.server.pojo.ServerInfo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brnel on 8/4/2017.
 */
public class GameUtilTest {

    @Test
    public void isLocalProcessViaLocalProcessName(){
        ServerInfo build = ServerInfo.newBuilder()
                .setLocalProcessName("localProcessName")
                .build();
        assertTrue(GameUtil.isLocalProcess(build));
    }

    @Test
    public void isLocalProcessViaAddress(){
        ServerInfo build = ServerInfo.newBuilder()
                .setAddress("127.0.0.1")
                .setLocalProcessName("localProcessName")
                .build();
        assertTrue(GameUtil.isLocalProcess(build));
    }

    @Test
    public void isNotLocalProcess(){
        ServerInfo build = ServerInfo.newBuilder()
                .setAddress("gatesum.net")
                .setPort(3200)
                .build();
        assertFalse(GameUtil.isLocalProcess(build));
    }

}