package functional.lambda.apis.calculator;

public class GrandImpl implements GrandApi {

    @Override
    public double doSomething(Calculator calc, String str, int i) {
        return calc.calculateSomething() * i + str.length();
    }

    public static void main(String...args) {
        GrandApi api = new GrandImpl();
        Calculator calc = new CalcImpl(20, 10d);
        double res = api.doSomething(calc, "abc", 2);
        System.out.println(res);
        res = api.doSomething(new Calculator() {
            public double calculateSomething() {
                return 20 * 10d;
            }
        },  "abc", 2);
        System.out.println(res);
        functional.lambda.apis.calculator.AnyImpl anyImpl = new functional.lambda.apis.calculator.AnyImpl();
        res = api.doSomething(anyImpl::doIt, "abc", 2);
        System.out.println(res);

        res = api.doSomething(() -> 20 * 10d, "abc", 2);
        System.out.println(res);

        int i = 20;
        double d = 10.0;
        res = api.doSomething(() -> i * d, "abc", 2);
        System.out.println(res);
    }
}
