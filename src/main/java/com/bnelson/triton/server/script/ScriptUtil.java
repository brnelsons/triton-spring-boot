package com.bnelson.triton.server.script;

import com.bnelson.triton.server.pojo.ServerInfo;

public class ScriptUtil {
    private ScriptUtil(){}

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getIsProcessRunningString(String processName){
        if(isWindows()) {
            return "tasklist /FI \"IMAGENAME eq "+processName+"\"";
        }else{
            return "";
        }
    }

    /**
     * @return true if process is running based on the results provided
     */
    public static boolean isProcessRunningFromResult(String processResult){
        if(isWindows()) {
            return !processResult.isEmpty()
                    && !processResult.contains("No Process exists for")
                    && !processResult.contains("INFO: No tasks are running which match the specified criteria.");
        }else if(isUnix()){
            return false;
        }else if(isMac()){
            return false;
        }
        throw new IllegalStateException("unknown operating system");
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );
    }

    public static ProcessBuilder getProcessBuilder(String command) {
        if(isWindows()) {
            return new ProcessBuilder("cmd", "/c", command);
        }else{
            return new ProcessBuilder("bash", "-c", command);
        }
    }

    public static String getKillCommand(ServerInfo serverInfo){
        Boolean force = serverInfo.getRequiresForceKill();
        if(isWindows()) {
            return "taskkill "+(force == null || force ? "/f /im " : "/im ") + serverInfo.getLocalProcessName();
        }else{
            throw new UnsupportedOperationException("cant run kill commands on this OS yet!");
        }
    }
}
