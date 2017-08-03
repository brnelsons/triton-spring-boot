package com.bnelson.triton.server.script;

/**
 * Created by brnel on 7/31/2017.
 */
public class ScriptUtil {
    private ScriptUtil(){}

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getIsProcessRunningString(String processName){
        if(isWindows()) {
            return "QPROCESS " + processName;
        }else{
            return "";
        }
    }

    /**
     * @return true if process is running based on the results provided
     */
    public static boolean isProcessRunningFromResult(String processResult){
        if(isWindows()) {
            return !processResult.isEmpty() && !processResult.startsWith("No Process exists for");
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
}
