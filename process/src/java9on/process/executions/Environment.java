package java9on.process.executions;

import java.util.Map;

public class Environment {
    public static void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("printenv").inheritIO();
        Map<String,String> env = pb.environment();
        env.put("FirstVar","First Variable");
        env.put("SecondVar", "Second Variable");
        try {
            Process p = pb.start();
            int exitValue = p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
