package functional.lambda;

import java.util.function.Consumer;

public class DemoThis {
    private String prop = "DemoProperty";
    public void method () {
        Consumer<String> consumer = s -> {
            System.out.println("Lambda accept(" + s + "):  this.prop=" + this.prop);
        };
        consumer.accept(this.prop);
        consumer = new Consumer<String> () {
            private String prop="ConsumerProperty";
            public void accept(String s) {
                System.out.println("Anonymous accept(" + s + ") : this.prop=" + this.prop);
            }
        };
        consumer.accept(this.prop);
    }
    public static void main(String...args) {
        DemoThis d = new DemoThis();
        d.method();
    }
}
