package functional.lambda.apis.calculator.lambda;

import functional.lambda.apis.calculator.AnyImpl;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GrandImpl implements GrandApi {
    @Override
    public double doSomething(Supplier<Double> supp, String str, int i) {
        return supp.get() * i + str.length();
    }

    @Override
    public double doSomethingWithFunction(Function<Integer, Integer> function, double num) {
        return function.apply(10) * num;
    }

    @Override
    public void doSomethingWithConsumer(Consumer<String> consumer, double num) {
        consumer.accept(Double.toString(num));
    }

    public static void main(String...args) {
        GrandApi api = new GrandImpl();
        Supplier<Double> supp = () -> 20 * 10d;
        double res = api.doSomething(supp, "abc", 2);
        System.out.println(res);

        res = api.doSomething(() -> 20 * 10d, "abc", 2);
        System.out.println(res);

        AnyImpl anyImpl = new AnyImpl();
        res = api.doSomething(anyImpl::doIt, "abc", 2);
        System.out.println(res);

        Consumer<String> consumer = System.out::println;
        api.doSomethingWithConsumer(consumer, 2d);

        api.doSomethingWithConsumer(System.out::println, 2d);

        int[] arr = new int[1];
        arr[0] = 1;
        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);

        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);

        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);
    }
}
