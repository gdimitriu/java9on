package java9on.process.executions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Iostat {
    static public void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder("iostat");
        //redirect the output and error stream
        pb.redirectError(new File("error")).redirectOutput(new File("output"));
        try {
            Process p = pb.start();
            int exitValue = p.waitFor();
            Files.lines(Paths.get("output")).forEach(l -> System.out.println(l));
            Files.lines(Paths.get("error")).forEach(l -> System.out.println(l));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
