package functional.multithread.executors.realLifeExample;

import java.util.concurrent.Callable;

public interface CallableWorker<Result> extends Callable<Result> {
    default String getName() { return "Anonymous";}
    default int getSleepSec() {return 1;}
}
