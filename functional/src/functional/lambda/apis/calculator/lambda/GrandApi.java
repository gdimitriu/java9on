package functional.lambda.apis.calculator.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface GrandApi {
    double doSomething(Supplier<Double> supp, String str, int i);
    double doSomethingWithFunction(Function<Integer, Integer> function, double num);
    void doSomethingWithConsumer(Consumer<String> consumer, double num);
}
