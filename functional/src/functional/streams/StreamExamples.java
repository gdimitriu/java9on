package functional.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples {
    public static void main(String...args) {
        List.of("This ", "is ", "created ","by ", "List.of().stream() ").stream().forEach(System.out::print);
        System.out.println();
        Set.of("This ", "is ", "created ", "by ", "Set.of().stream() ").stream().forEach(System.out::print);
        System.out.println();
        Map.of(1, "This ", 2, "is ", 3, "created ", 4, "by ", 5, "Map.of().entrySet().stream() ").entrySet().stream().forEach(System.out::print);
        System.out.println();
        String[] array = {"That ", "is ", "an ", "Arrays.stream(array)"};
        Arrays.stream(array).forEach(System.out::print);
        System.out.println();
        String[] array1 = {"That ", "is ", "an ", "Arrays.stream(array1,0,2) "};
        Arrays.stream(array1,0,2).forEach(System.out::print);
        System.out.println();
        String[] array2 = {"That ", "is ", "a ", "Stream.of(array2) "};
        Stream.of(array2).forEach(System.out::print);
        System.out.println();
        Stream.of("That ", "is ", "a ", "Stream.of(literals) " ).forEach(System.out::print);
        System.out.println();
        Stream.generate(() -> "generated ").limit(3).forEach(System.out::print);
        System.out.println();
        System.out.print("Stream.iterate().limit(10): ");
        Stream.iterate(0, i -> i+1).limit(10).forEach(System.out::print);
        System.out.println();
        System.out.print("Stream.iterate(Predicate < 10): ");
        Stream.iterate(0, i -> i < 10, i -> i + 1).forEach(System.out::print);
        System.out.println();
        System.out.print("IntStream.range(0,9): ");
        IntStream.range(0,9).forEach(System.out::print);
        System.out.println();
        System.out.print("IntStream.rangeClosed(0,9): ");
        IntStream.rangeClosed(0,9).forEach(System.out::print);
        System.out.println();
    }
}
