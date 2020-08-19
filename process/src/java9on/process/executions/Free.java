package java9on.process.executions;

import java.util.concurrent.TimeUnit;

public class Free {
    public static void main(String...args) {
        //ProcessBuilder pBuilder = new ProcessBuilder("free","-m");
        //equivalent to
        ProcessBuilder pBuilder = new ProcessBuilder();
        pBuilder.command("free", "-m");
        try {
            Process p = pBuilder.inheritIO().start();
            System.out.println("PID = " +  p.pid());
            if (p.waitFor(1, TimeUnit.SECONDS)) {
                System.out.println("process completed successfully");
            } else {
                System.out.println("waiting time elapsed, process did not complete");
                System.out.println("destroying process forcibly");
                p.destroyForcibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
