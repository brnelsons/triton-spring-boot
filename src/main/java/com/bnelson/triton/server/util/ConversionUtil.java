package com.bnelson.triton.server.util;

import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;

/**
 * Created by brnel on 7/20/2017.
 */
public class ConversionUtil {

    public static GameInfoRPC convert(GameInfo info){
        GameInfoRPC rpc = new GameInfoRPC();
        rpc.setName(info.getName());
        rpc.setDescription(info.getDescription());
        return rpc;
    }

    public static GameInfo convert(GameInfoRPC rpc){
        return GameInfo.newBuilder()
                .setName(rpc.getName())
                .setDescription(rpc.getDescription())
                .build();
    }

    public static ServerInfoRPC convert(ServerInfo info){
        ServerInfoRPC rpc = new ServerInfoRPC();
        rpc.setAddress(info.getAddress());
        return rpc;
    }

//    we do not expose the server info to the client so this will have missing fields.
//    public static ServerInfo convert(ServerInfoRPC rpc){
//        return
//    }

}
