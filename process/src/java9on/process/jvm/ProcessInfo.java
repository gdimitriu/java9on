package java9on.process.jvm;

import java.time.Instant;

public class ProcessInfo {
    public static void main(String...args) {
        ProcessHandle handle = ProcessHandle.current();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ProcessHandle.Info  info = handle.info();
        System.out.println("Command line: " + info.commandLine().get());
        System.out.println("Command: " + info.command().get());
        System.out.println("Arguments: " + String.join(" ", info.arguments().get()));
        System.out.println("User: " + info.user().get());
        System.out.println("Start: " + info.startInstant().get());
        System.out.println("Total CPU Duration: " + info.totalCpuDuration().get().toMillis() + " ms");
        System.out.println("PID: " + handle.pid());
        Instant end = Instant.now();
        System.out.println("End: " + end);
    }
}
