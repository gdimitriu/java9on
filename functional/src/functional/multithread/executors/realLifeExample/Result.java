package functional.multithread.executors.realLifeExample;

public class Result {
    private int sleepSec;
    private int result;

    private String workerName;
    public Result(String workerName, int sleepSec, int result) {
        this.workerName = workerName;
        this.sleepSec = sleepSec;
        this.result = result;
    }

    public int getSleepSec() {
        return sleepSec;
    }

    public int getResult() {
        return result;
    }

    public String getWorkerName() {
        return workerName;
    }
}
