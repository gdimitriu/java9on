package functional.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestFunctionalInterfacesConvertedToLambda {
    private static Supplier<String> applyCompareAndSay(int i, Function<Integer, Double> func,
                                                       Predicate<Double> compare, String message) {
        return () -> {
            double v = func.apply(i);
            return (compare.test(v) ? v + " is " : v + " is not ") + message;
        };
    }

    public static void main(String... args) {
        Function<Integer, Double> ourFunc = i -> i * 10.0;
        System.out.println(ourFunc.apply(1));
        Consumer<String> consumer = s -> System.out.println("The " + s + " is consumed.");
        consumer.accept("Hello!");
        Supplier<String> supplier = () -> {
            return "Success";
        };
        System.out.println(supplier.get());
        Predicate<Double> predicate = num -> {
            System.out.println("Test if " + num + " is smaller than 20");
            return num < 20;
        };
        System.out.println(predicate.test(10.0) ? "10 is smaller" : "10 is bigger");

        Function<Integer, Double> multiplyBy10 = i -> i * 10.0;
        System.out.println("1 * 10.0 => " + multiplyBy10.apply(1));

        Function<Integer, Double> multiplyBy30 = i -> i * 30.0;
        System.out.println("1 * 30.0 => " + multiplyBy30.apply(1));

        Function<Double, Double> substract7 = x -> x - 7.0;
        System.out.println("10.0 - 7.0 => " + substract7.apply(10.0));

        Consumer<String> sayHappyToSee = s -> System.out.println(s + " Happy to see you again!");
        sayHappyToSee.accept("Hello!");

        Predicate<Double> isSmallerThan20 = x -> x < 20d;
        System.out.println("10.0 is smaller than 20.0 => " + isSmallerThan20.test(10d));

        Predicate<Double> isBiggerThan18 = x -> x > 18d;
        System.out.println("10.0 is bigger than 18.0 => " + isBiggerThan18.test(10d));

        Supplier<String> supp = applyCompareAndSay(1, multiplyBy10, isSmallerThan20, " hello");
        System.out.println(supp.get());

        Supplier<String> compare1by30Less7TwiceTo18And20 = applyCompareAndSay(1, x-> x*30.0 - 7.0 - 7.0,
                x-> x < 20 && x > 18, "between 18 and 20");
        System.out.println("compare (1 * 30 - 7 - 7) and the range 18 to 20 =>" + compare1by30Less7TwiceTo18And20.get() );
    }
}
