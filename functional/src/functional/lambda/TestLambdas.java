package functional.lambda;

import java.util.function.BiFunction;

public class TestLambdas {

    public static void main(String...args) {
        BiFunction<Integer, String, Double> demo = (x,y) -> x*10d +Double.parseDouble(y);
        System.out.println(demo.apply(1,"100"));
        demo = (x,y) -> {
            double v = 10d;
            return x *v + Double.parseDouble(y);
        };
        System.out.println(demo.apply(1,"100"));
    }
}
