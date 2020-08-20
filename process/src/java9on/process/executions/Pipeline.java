package java9on.process.executions;

import java.util.List;

public class Pipeline {
    public static void main(String...args) {
        String user = System.getenv("USER");
        List<ProcessBuilder> pipeline = List.of( new ProcessBuilder("ps","-aef"),
                new ProcessBuilder("grep", user).redirectOutput(ProcessBuilder.Redirect.INHERIT));
        try {
            List<Process> proceses = ProcessBuilder.startPipeline(pipeline);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
