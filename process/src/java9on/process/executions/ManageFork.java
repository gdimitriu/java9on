package java9on.process.executions;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ManageFork {
    public static void main(String...args) {
        ProcessBuilder pb = new ProcessBuilder("sleep", "40");
        try {
            Process p = pb.inheritIO().start();
            Instant instantStart = Instant.now();
            p.waitFor(10, TimeUnit.SECONDS);
            boolean isAlive = p.isAlive();
            System.out.println("Process alive ? : " + isAlive);
            boolean normalTermination = p.supportsNormalTermination();
            System.out.println("Normal Termination? : " + normalTermination);
            p.destroy();
            isAlive = p.isAlive();
            System.out.println("Process alive after destroy ? : " + isAlive);
            Instant instantBeforeDestroy = Instant.now();
            p.destroyForcibly();
            isAlive = p.isAlive();
            System.out.println("Process alive after termination ? : " + isAlive);
            p.waitFor();
            Instant instantAfterFinish = Instant.now();
            isAlive = p.isAlive();
            System.out.println("Process alive after elapsed time ? : " + isAlive);
            System.out.println("StartTime:" + instantStart + " At DestroyForcibly: " + instantBeforeDestroy + " Final: " + instantAfterFinish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
