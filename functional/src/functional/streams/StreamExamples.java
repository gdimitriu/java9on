package functional.streams;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
        System.out.println("Files.list(dir): ");
        Path dir = FileSystems.getDefault().getPath("src/functional/lambda/");
        try(Stream<Path> stream = Files.list(dir)) {
            stream.forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Files.find(dir, depth, predicate");
        dir = FileSystems.getDefault().getPath("src/functional/referencemethods");
        BiPredicate<Path, BasicFileAttributes> select = (p, b) -> p.getFileName().toString().contains("Food");
        try (Stream<Path> stream = Files.find(dir,2,select)) {
            stream.map(path -> path.getFileName()).forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //other stream methods
        int sum = Stream.of(1,2,3,4,5,6,7,8,9).filter(i -> i%2 != 0).peek(i -> System.out.print(i)).mapToInt(Integer::intValue).sum();
        System.out.println("\nsum = " + sum);
        System.out.println("Files.lines().dropWhile().takeWhile(): ");
        try (Stream<String> stream = Files.lines(Paths.get("src/functional/streams/StreamExamples.java"))) {
            stream.dropWhile(l -> !l.contains("dropWhile().takeWhile()"))
                    .takeWhile(l -> !l.contains("} catc" + "h"))
                    .forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Stream.of("That ", "is ", "a ", "Stream.of(literals").map(s -> s.contains("i")).forEach(System.out::println);
        Stream.of("That ", "is ", "a ", "Stream.of(literals").filter(s -> s.contains("Th")).flatMap(s -> Pattern.compile("(?!^)").splitAsStream(s)).forEach(System.out::println);
        Stream.concat(Stream.of(4,5,6), Stream.of(1,2,3)).forEach(System.out::print);
        System.out.println();
        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9)).flatMap(Function.identity()).forEach(System.out::print);
        System.out.println();
        //terminal operations
        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9)).reduce(Stream::concat).orElseGet(Stream::empty).forEach(System.out::print);
        System.out.println();
        Stream.of("3","2","1").parallel().forEach(System.out::print);
        System.out.println();
        Stream.of("3","2","1").parallel().forEachOrdered(System.out::print);
        System.out.println();
        Stream.of("That ", "is ", "a ", null, "Stream.of(literals)").map(Optional::ofNullable).filter(Optional::isPresent).map(Optional::get).map(String::toString).forEach(System.out::print);
        System.out.println();
        try {
            Stream.of("That ", "is ", "a ", null, "Stream.of(literals)").map(Optional::of).filter(Optional::isPresent).map(Optional::get).map(String::toString).forEach(System.out::print);
            System.out.println();
        } catch (NullPointerException r) {
            System.out.println("Catch NullPointerException");
            r.getStackTrace();
        }
        sum = Stream.of(1,2,3).reduce((p,e) -> p + e).orElse(0);
        System.out.println("Stream.of(1,2,3).reduce(acc): " + sum);
        sum = Stream.of(1,2,3).reduce(0, (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).reduce(0, acc): " + sum);
        String sumS = Stream.of(1,2,3).reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).reduce(0, acc, combiner): " + sumS);
        sumS = Stream.of(1,2,3).parallel().reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).reduce(0, acc, combiner): " + sumS);
        sumS = Stream.of(1,2,3).map(i -> i.toString() + ",").reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).reduce(, acc): " + sumS.substring(0, sumS.length() - 1));
        sumS = Stream.of(1,2,3).parallel().map(i -> i.toString() + ",").reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).reduce(, acc): " + sumS.substring(0, sumS.length() - 1));
        //some collectors
        double aa = Stream.of(1,2,3).map(Thing::new).collect(Collectors.averagingInt(Thing::getSomeInt));
        System.out.println("stream(1,2,3).averagingInt(): " + aa);
        String as = Stream.of(1,2,3).map(Thing::new).map(Thing::getSomeString).collect(Collectors.joining(","));
        System.out.println("stream(1,2,3).joining(): " + as);
        String ss = Stream.of(1,2,3).map(Thing::new).map(Thing::getSomeString).collect(Collectors.joining(",","[","]"));
        System.out.println("stream(1,2,3).joining(,[,]): " + ss);
        //toArray
        Object[] os = Stream.of(1,2,3).toArray();
        Arrays.stream(os).forEach(System.out::print);
        System.out.println();
        String[] sts = Stream.of(1,2,3).map(i -> i.toString()).toArray(String[]::new);
        Arrays.stream(sts).forEach(System.out::print); //we have to open a new stream because toArray si terminal
        System.out.println();
    }
}
