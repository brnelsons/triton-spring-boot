package com.bnelson.triton.shared.rpc;

import java.io.Serializable;

/**
 * Created by brnel on 6/15/2017.
 */
public enum ConnectionStatusRPC implements Serializable {
    RUNNING,
    STOPPED,
    UNKNOWN
}
