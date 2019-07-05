package functional.referencemethods;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class FoodTest {
    public static void main(String...args) {
        Food food = new Food();
        Supplier<String> supplier = food::getFavorite;
        System.out.println("supplier..get() => " + supplier.get());
        Function<Integer, String> func = food::getFavorite;
        System.out.println("func.getFavorite(1) => " + func.apply(1));
        System.out.println("func.getFavorite(2) => " + func.apply(2));
        Supplier<Food> constrFood = Food::new;
        food = constrFood.get();
        System.out.println("Food favorite => " + food.getFavorite());
        Function<String, Food> constrFood1 = Food::new;
        food = constrFood1.apply("Donuts!");
        System.out.println("Food favorite => " + food.getFavorite());
        food = constrFood1.apply("Carrot!");
        System.out.println("Food favorite => " + food.getFavorite());
        BiFunction<String, String, Food>  arg2Constructor = Food::new;
        food = arg2Constructor.apply("Donut ", " Milk");
        System.out.println("Food favorite => "  + food.getFavorite());
        Functionalconstructor<String,String,String, Food> arg3Constructor = Food::new;
        food = arg3Constructor.constructor("Donut ", " Milk", " Coffe");
        System.out.println("Food favorite => "  + food.getFavorite());
    }
}
