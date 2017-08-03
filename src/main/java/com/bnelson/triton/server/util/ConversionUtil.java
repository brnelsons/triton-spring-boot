package com.bnelson.triton.server.util;

import com.bnelson.triton.server.pojo.Command;
import com.bnelson.triton.server.pojo.ExternalLink;
import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.ExternalLinkRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;

import java.util.ArrayList;

/**
 * Created by brnel on 7/20/2017.
 */
public class ConversionUtil {

    public static GameInfoRPC convert(GameInfo info){
        GameInfoRPC rpc = new GameInfoRPC();
        rpc.setId(info.getId());
        rpc.setName(info.getName());
        rpc.setDescription(info.getDescription());
        return rpc;
    }

    public static GameInfo convert(GameInfoRPC rpc){
        return GameInfo.newBuilder()
                .setId(rpc.getId())
                .setName(rpc.getName())
                .setDescription(rpc.getDescription())
                .build();
    }

    public static ServerInfoRPC convert(ServerInfo info){
        ServerInfoRPC rpc = new ServerInfoRPC();
        rpc.setAddress(info.getAddress());
        return rpc;
    }

    public static CommandInfoRPC convert(Command command) {
        CommandInfoRPC infoRPC = new CommandInfoRPC();
        infoRPC.setName(command.getName());
        infoRPC.setDescription(command.getDescription());
        return infoRPC;
    }

    public static ExternalLinkRPC convert(ExternalLink externalLink){
        return new ExternalLinkRPC(externalLink.getName(), externalLink.getUrl());
    }

    public static ExternalLink convert(ExternalLinkRPC externalLink){
        return new ExternalLink(externalLink.getName(), externalLink.getUrl());
    }

//    we do not expose the server info to the client so this will have missing fields.
//    public static ServerInfo convert(ServerInfoRPC rpc){
//        return
//    }

}
