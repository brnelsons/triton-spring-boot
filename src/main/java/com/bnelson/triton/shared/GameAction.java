package com.bnelson.triton.shared;

import java.io.Serializable;

/**
 * Created by brnel on 6/21/2017.
 */
public class GameAction implements Serializable{

    private String actionName;
    private GameScriptType scriptType;
    private String script;

    public GameAction() {}

    public GameAction(String actionName, GameScriptType scriptType, String script) {
        this.actionName = actionName;
        this.scriptType = scriptType;
        this.script = script;
    }

    public String getActionName() {
        return actionName;
    }

    public GameAction setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public GameScriptType getScriptType() {
        return scriptType;
    }

    public GameAction setScriptType(GameScriptType scriptType) {
        this.scriptType = scriptType;
        return this;
    }

    public String getScript() {
        return script;
    }

    public GameAction setScript(String script) {
        this.script = script;
        return this;
    }
}
