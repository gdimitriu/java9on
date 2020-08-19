package java9on.process.executions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tree {
    static public void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("tree").inheritIO();
        pb.directory(new File(pb.environment().get("HOME") + "/Desktop"));
        try {
            Process p = pb.start();
            int exitValue = p.waitFor();
        } catch(Exception e) {

        }
    }
}
