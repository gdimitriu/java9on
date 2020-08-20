package java9on.process.executions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChildrenScript {
    public static void main(String...args) {
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                processes.add(new ProcessBuilder()
                        .directory(new File(System.getenv().get("PWD") + "/src/java9on/process/executions"))
                        .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                        .command("/bin/bash", "./scriptwsleep.sh").inheritIO().start());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ProcessHandle current = ProcessHandle.current();
        current.children().forEach(ch -> {
            System.out.println(ch.pid());
        });
        current.descendants().forEach(ch -> {
            ch.destroyForcibly();
        });
        processes.parallelStream().forEach(p -> {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
