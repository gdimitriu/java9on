package functional.multithread.executors.realLifeExample;

public class CallableWorkerImpl implements CallableWorker<Result> {

    private int sleepSec;
    private String name;
    public CallableWorkerImpl(String name, int sleepSec) {
        this.sleepSec = sleepSec;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSleepSec() {
        return sleepSec;
    }

    @Override
    public Result call() throws Exception {
        try {
            Thread.sleep(sleepSec * 1000);
        } catch (Exception ex) {
            System.out.println("Caught in CallableWorker for worker= " + name + " : "+ ex.getClass().getName());
        }
        return new Result(name, sleepSec, 42);
    }

}

