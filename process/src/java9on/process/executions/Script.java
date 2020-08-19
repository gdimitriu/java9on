package java9on.process.executions;

import java.io.File;

public class Script {
    public static void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.environment().put("MY_VARIABLE","The variable from java process");
        pb.directory(new File(System.getenv().get("PWD") + "/src/java9on/process/executions"));
        pb.command("/bin/bash","./script.sh").inheritIO();
        try {
            Process p = pb.start();
            int exitValue = p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
