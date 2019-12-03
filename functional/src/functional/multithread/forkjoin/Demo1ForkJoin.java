package functional.multithread.forkjoin;

import functional.multithread.forkjoin.api.DateLocation;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.concurrent.ForkJoinPool;

public class Demo1ForkJoin {
    public static void main(String...args) {
        Demo1ForkJoin demo = new Demo1ForkJoin();
        System.out.println("---------fork------------------------");
        demo.demo1_ForkJoin_fork_join();
        System.out.println("---------commonPool-execute----------");
        demo.demo2_ForkJoin_execute_join();
        System.out.println("---------commonPool-invoke-----------");
        demo.demo3_ForkJoin_invoke();
    }

    private void demo1_ForkJoin_fork_join() {
        System.out.println();
        AverageSpeed averageSpeed = createTask();
        averageSpeed.fork();
        double result = averageSpeed.join();
        System.out.println("result = " + result);
    }

    private void demo2_ForkJoin_execute_join() {
        AverageSpeed averageSpeed = createTask();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        commonPool.execute(averageSpeed);
        double result = averageSpeed.join();
        System.out.println("result = " + result);
    }

    private void demo3_ForkJoin_invoke() {
        AverageSpeed averageSpeed = createTask();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        double result = commonPool.invoke(averageSpeed);
        System.out.println("result = " + result);
    }

    private static AverageSpeed createTask(){
        DateLocation dateLocation = new DateLocation(Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
        return new AverageSpeed(dateLocation, 10, 1001, 100);
    }
}
