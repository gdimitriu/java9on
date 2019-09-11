package functional.multithread.threads;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class SynchronizationThreadsDemo {
    public static void main(String...args) {
        SynchronizationThreadsDemo demo = new SynchronizationThreadsDemo();
        demo.runCompute();
        demo.realrunCompute();
    }

    private void runCompute() {
        Calculator c = new Calculator();
        Thread thr1 = new Thread(() -> System.out.println(IntStream.range(1,4)
                .peek(x -> DoubleStream.generate(new Random()::nextDouble).limit(10)).mapToDouble(c::calculate).sum()));
        Thread thr2 = new Thread(() -> System.out.println(IntStream.range(1,4).mapToDouble(c::calculate).sum()));
        thr1.start();
        thr2.start();
    }
    private void realrunCompute() {
        Thread thr1 = new Thread(() -> System.out.println(IntStream.range(1,4)
                .peek(x -> DoubleStream.generate(new Random()::nextDouble).limit(10)).mapToDouble(x -> {
                    Calculator c = new Calculator();
                    return c.calculate(x);
                }).sum()));
        Thread thr2 = new Thread(() -> System.out.println(IntStream.range(1,4).mapToDouble( x -> {
            Calculator c = new Calculator();
            return c.calculate(x);
        }).sum()));
        thr1.start();
        thr2.start();
    }
    private class Calculator {
        private double prop;
        public double calculate(int i) {

//            synchronized(this) {
                this.prop = 2.0 * i;
            DoubleStream.generate(new Random()::nextDouble).limit(10);
                return Math.sqrt(this.prop);
//            }
        }
    }
}
