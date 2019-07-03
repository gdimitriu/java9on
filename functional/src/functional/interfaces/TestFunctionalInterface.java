package functional.interfaces;

import java.util.function.*;

public class TestFunctionalInterface {
    public static void main(String... args) {
        Function<Integer, Double> ourFunction = new Function<Integer, Double>() {
          public Double apply(Integer i) {
              return i * 10.0;
          }
        };
        System.out.println(ourFunction.apply(1));
        Consumer<String> ourConsumer = new Consumer<String>() {
            public void accept(String s) {
                System.out.println("The " + s  +  " is consumed.");
            }
        };
        ourConsumer.accept("Hello !");
        Supplier<String> ourSupplier = new Supplier<String> () {
            public String get() {
                return "Success given";
            }
        };
        System.out.println("Our Supplier had produced " + ourSupplier.get());
        Predicate<Double> ourPredicate = new Predicate<Double> () {
            public boolean test (Double val) {
                System.out.println("Test is the value is bigger than 10");
                return val >10;
            }
        };
        System.out.println("Testing that the 12 is bigger than 10 and result is " + ourPredicate.test(12.0));
        BiFunction<String, Integer, Double> ourbiFunction = new BiFunction<String, Integer, Double> () {
            public Double apply(String s, Integer i) {
                return (s.length() * 10d)/i;
            }
        };
        System.out.println("Length of the string str is multiplly by 10 and divided by 2 so result should be 15 and is " + ourbiFunction.apply("str", 2));
        Function<Integer, Double> multiplyBy10 = createMultiplyBy(10d);
        System.out.println("Multiply by 10 of 2 is " + multiplyBy10.apply(2));
        Function<Double, Double> ddFunction = new Function<Double, Double> () {
            public Double apply(Double d) {
                return d *2 ;
            }
        };
        Function<Integer, Double> bigFunction = ourFunction.andThen(ddFunction);
        System.out.println("multiple :" + bigFunction.apply(10));
    }

    private static Function<Integer, Double> createMultiplyBy(double num) {
        Function<Integer, Double> ourfunction = new Function<Integer, Double> () {
            public Double apply(Integer i) {
                return i * num;
            }
        };
        return ourfunction;
    }
}
