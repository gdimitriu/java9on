package java9on.process.system;

import java.util.stream.Stream;

public class AllProcesses {
    public static void main(String...args) {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.forEach(ph -> {
            ProcessHandle.Info info = ph.info();
            System.out.println(info.command().orElse("") + " " + info.user().orElse(""));
        });
    }
}
