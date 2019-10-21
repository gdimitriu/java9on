package functional.multithread.executors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorsDemo1 {
    public static void main(String...args) {
        ExecutorsDemo1 demo1 = new ExecutorsDemo1();
        demo1.executorExample();
        demo1.executorExampleRefactorized();
    }

    public static void executorExample() {
        int shutdownDelaySec = 1;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> System.out.println("Worker One did the job.");
        executorService.execute(runnable);
        runnable = () -> System.out.println("Worker Two did the job.");
        Future future = executorService.submit(runnable);
        try {
            executorService.shutdown();
            executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around executorService.awaitTermination(): " + ex.getClass().getName());
        } finally {
             if (!executorService.isTerminated()) {
                 if (future != null && !future.isDone() && !future.isCancelled()) {
                     System.out.println("Canceling the task...");
                     future.cancel(true);
                 }
             }
        }
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println(runnables.size() + " tasks are waiting to be executed. Service stopped. ");
    }

    /**
     * shutdown and cancel task.
     * @param execService the executor service
     * @param shutdownDelaySec the delay until kill
     * @param name the name of the task
     * @param future the future returned
     */
    private void shutdownAndCancelTask(ExecutorService execService, int shutdownDelaySec, String name, Future future) {
        try {
            execService.shutdown();
            System.out.println("Waiting for " + shutdownDelaySec + " sec before shutting down service...");
            execService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around " + "execService.awaitTermination():" + ex.getClass().getName());
        } finally {
             if (!execService.isTerminated()) {
                 System.out.println("Terminating remaining tasks...");
                 if (future != null && !future.isDone() && !future.isCancelled()) {
                     System.out.println("Canceling task " + name + " ...");
                     future.cancel(true);
                 }
             }
        }
        System.out.println("Calling execService.shutdownNow(" + name + ")...");
        List<Runnable> runnables = execService.shutdownNow();
        System.out.println(runnables.size() + " tasks are waiting to be executed. Service stopped.");
    }

    private void executeAndSubmit(ExecutorService execService, int shutdownDelaySec, int threadSleepsSec) {
        System.out.println("shutdownDelaySec = " + shutdownDelaySec + ", threadSleepSec = " + threadSleepsSec);
        Runnable runnable = () -> {
          try {
              Thread.sleep(threadSleepsSec * 1000);
              System.out.println("Worker One did the job.");
          } catch (Exception ex) {
              System.out.println("Caught around One Thread.sleep():" + ex.getClass().getName());
          }
        };
        execService.execute(runnable);
        runnable = () -> {
            try {
                Thread.sleep(threadSleepsSec * 1000);
                System.out.println("Worker Two did the job.");
            } catch (Exception ex) {
                System.out.println("Caught around Two Thread.sleep():" + ex.getClass().getName());
            }
        };
        Future future = execService.submit(runnable);
        shutdownAndCancelTask(execService, shutdownDelaySec, "Two", future);
    }

    public void executorExampleRefactorized() {
        System.out.println("Executors.newSingleThreadExecutor():");
        ExecutorService execService = Executors.newSingleThreadExecutor();
        executeAndSubmit(execService,3,3);
        System.out.println();
        System.out.println("Executors.newCachedThreadPool():");
        execService = Executors.newCachedThreadPool();
        executeAndSubmit(execService,3,3);

        System.out.println();
        int poolSize = 3;
        System.out.println("Executors.newFixedThreadPool(" + poolSize + "):");
        execService = Executors.newFixedThreadPool(poolSize);
        executeAndSubmit(execService,3,3);
    }
}
