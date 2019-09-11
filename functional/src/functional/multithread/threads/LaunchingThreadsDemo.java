package functional.multithread.threads;

import java.util.stream.IntStream;

public class LaunchingThreadsDemo {
    public static void main(String...args) {
        threadDemo();
        runnableDemo();
        functionalDemo();
        functionalDemo1();
        interruptThreadDemo();
    }

    private static class AThread extends Thread {
        int i1, i2;
        AThread(int i1, int i2) {
            this.i1 = i1;
            this.i2 = i2;
        }
        public void run() {
            IntStream.range(i1,i2).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
        }
    }
    private static class ARunnable implements Runnable {
        int i1, i2;
        ARunnable(int i1, int i2) {
            this.i1 = i1;
            this.i2 = i2;
        }
        public void run() {
            IntStream.range(i1,i2).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
        }
    }
    private static int doSomething(int i) {
        IntStream.range(i,99999).asDoubleStream().map(Math::sqrt).average();
        return i;
    }

    private static void threadDemo() {
        System.out.println("threadDemo");
        Thread thr1 = new AThread(1,4);
        thr1.start();
        Thread thr2 = new AThread(11,14);
        thr2.start();
        IntStream.range(21,24).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
    }

    private static void runnableDemo() {
        System.out.println("runnableDemo");
        Thread thr1 = new Thread(new ARunnable(1,4));
        Thread thr2 = new Thread(new ARunnable(11,14));
        thr1.start();
        thr2.start();
        IntStream.range(21,24).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
    }

    private static void functionalDemo() {
        System.out.println("functionalDemo");
        Thread thr1 = new Thread(() -> IntStream.range(1,4).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println));
        Thread thr2 = new Thread(() -> IntStream.range(11,14).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println));
        thr1.start();
        thr2.start();
        IntStream.range(21,24).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
    }

    private static void functionalDemo1() {
        System.out.println("functionalDemo1");
        Thread thr1 = new Thread(() ->runImpl(1,4), "First Thread" );
        Thread thr2 = new Thread(() -> runImpl(11,14), "Second Thread" );
        System.out.println("Id=" + thr1.getId() + ", " + thr1.getName() + ", priority=" + thr1.getPriority() + ", state=" + thr1.getState());
        System.out.println("Id=" + thr2.getId() + ", " + thr2.getName() + ", priority=" + thr2.getPriority() + ", state=" + thr2.getState());
        thr1.start();
        thr2.start();
        runImpl(21,24);
    }

    private static void runImpl(int i1, int i2) {
        IntStream.range(i1,i2).peek(LaunchingThreadsDemo::doSomething).forEach(System.out::println);
    }

    private static class BRunnable implements Runnable {

        int i1, result;
        public BRunnable(int i1) {
            this.i1 = i1;
        }

        public int getCurrentResult() {
            return this.result;
        }

        @Override
        public void run() {
            for(int i = i1; i < i1 + 6; i++) {
                this.result = i;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }
        }
    }

    private static void interruptThreadDemo() {
        BRunnable r1 = new BRunnable(1);
        Thread thr1 = new Thread(r1);
        thr1.start();
        IntStream.range(21, 29).peek( i -> thr1.interrupt()).filter((i -> {
            int res = r1.getCurrentResult();
            System.out.print(res + " => ");
            return res %2 == 0;
        })).forEach(System.out::println);
    }
}
