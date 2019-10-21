package functional.multithread.executors.realLifeExample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RealLifeExampleMain {
    public static void main(String...args) {
        RealLifeExampleMain main = new RealLifeExampleMain();
        List<CallableWorker<Result>>  callables = main.createListOfCallables(1);
        System.out.println("Executors.newSingleThreadExecutor():");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        main.invokeAllCallables(executorService, 6, callables);

        System.out.println("InvokeAny ... ");
        executorService = Executors.newSingleThreadExecutor();
        main.invokeAnyCallables(executorService, 6, callables);
    }

    private List<CallableWorker<Result>> createListOfCallables(int nSec) {
        return List.of(new CallableWorkerImpl("One", nSec),
                new CallableWorkerImpl("Two", 2 * nSec),
                new CallableWorkerImpl("Three", 3* nSec));
    }

    private void invokeAllCallables(ExecutorService execService, int shutdownDelaySec, List<CallableWorker<Result>> callables) {
        List<Future<Result>> futures = new ArrayList<>();
        try {
            futures = execService.invokeAll(callables, shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around execService.invokeAll():" + ex.getClass().getName());
        }
        try {
            execService.shutdown();
            System.out.println("Waiting for " + shutdownDelaySec + " sec before terminating all tasks...");
            execService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around awaitTermination(): " + ex.getClass().getName());
        } finally {
            if (!execService.isTerminated()) {
                System.out.println("Terminating remaining tasks...");
                for(Future<Result> future : futures) {
                    if (!future.isDone() && !future.isCancelled()) {
                        try {
                            System.out.println("Canceling task " + future.get(shutdownDelaySec, TimeUnit.SECONDS).getWorkerName());
                            future.cancel(true);
                        } catch (Exception ex) {
                            System.out.println("Caught at canceling task " + ex.getClass().getName());
                        }
                    }
                }
            }
            System.out.println("Calling execService.shutdownNow()...");
            execService.shutdownNow();
        }
        printResults(futures, shutdownDelaySec);
    }

    private void invokeAnyCallables(ExecutorService execService, int shutdownDelaySec, List<CallableWorker<Result>> callables) {
        Result result = null;
        try {
            result = execService.invokeAny(callables, shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around execService.invokeAny():" + ex.getClass().getName());
        }
        try {
            execService.shutdown();
            System.out.println("Waiting for " + shutdownDelaySec + " sec before terminating all tasks...");
            execService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Caught around awaitTermination(): " + ex.getClass().getName());
        } finally {
            if (result == null) {
                System.out.println("No result from execService.invokeAny()");
            } else {
                System.out.println("Worker " + result.getWorkerName() +
                        " slepts " + result.getSleepSec() +
                        " sec. Result = " + result.getResult());
            }

            System.out.println("Calling execService.shutdownNow()...");
            execService.shutdownNow();
        }
    }

    private void printResults(List<Future<Result>>  futures, int timeoutSec) {
        System.out.println("Results from futures: ");
        if (futures == null || futures.size() == 0) {
            System.out.println("No results. Futures" + (futures == null ? " = null" : ".size() = 0"));
        } else {
            for (Future<Result> future : futures) {
                try {
                    if (future.isCancelled()) {
                        System.out.println("Worker is canceled.");
                    } else {
                        Result result = future.get(timeoutSec, TimeUnit.SECONDS);
                        System.out.println("Worker " + result.getWorkerName() + " slept " + result.getSleepSec() +
                                " sec. Result = " + result.getResult());
                    }
                } catch (Exception ex) {
                    System.out.println("Caught while getting result : " + ex.getClass().getName());
                }
            }
        }
    }
}
