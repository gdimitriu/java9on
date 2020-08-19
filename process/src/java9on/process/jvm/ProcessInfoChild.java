package java9on.process.jvm;

public class ProcessInfoChild {
    public static void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder("sleep","20");
        try {
            Process p = pb.inheritIO().start();
            ProcessHandle handle = p.toHandle();
            ProcessHandle.Info info = handle.info();
            //if we wait before end of the process we could not get info
            int exitValue = p.waitFor();
            System.out.println("Command line: " + info.commandLine());
            System.out.println("Command: " + info.command());
            System.out.println("PID: " + handle.pid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
