package functional.streams.collections;

import java.util.*;

import static java.util.Map.entry;

public class MainTestsCollections {

    public static void before9() {
        //list
        List<String> list = new ArrayList<>();
        list.add("This ");
        list.add("is ");
        list.add("build ");
        list.add("by ");
        list.add("list.add() ");
        list.forEach(System.out::print);
        System.out.println();
        Arrays.asList("This ", "is ", "created ", "by ", "Arrays.asList() ").forEach(System.out::print);
        System.out.println();
        //set
        Set<String> set = new HashSet<>();
        set.add("This ");
        set.add("is ");
        set.add("build ");
        set.add("by ");
        set.add("set.add() ");
        set.forEach(System.out::print);
        System.out.println();
        new HashSet<>(Arrays.asList("This ", "is ", " created ", "by ", " new HashSet(Arrays.asList()) ")).forEach(System.out::print);
        System.out.println();
        //map
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "This ");
        map.put(2,"is ");
        map.put(3, "build ");
        map.put(4, "by ");
        map.put(5, "map.put() ");
        map.entrySet().forEach(System.out::print);
        System.out.println();
    }
    public static void after9() {
        List.of("This ", "is ", "created ", "by ", "List.of() ").forEach(System.out::print);
        System.out.println();
        Set.of("This ", "is ", "created ", "by ", "Set.of() ").forEach(System.out::print);
        System.out.println();
        Map.of(1, "This ", 2, "is ", 3, "created ", 4, "by ", 5, "Map.of() ").entrySet().forEach(System.out::print);
        System.out.println();
        Map.ofEntries(
                entry(1,"This "),
                entry(2, "is "),
                entry(3, "created "),
                entry(4, "by "),
                entry(5, "Map.ofEntries() ")
        ).entrySet().forEach(System.out::print);
        System.out.println();
        List<String> list = List.of("This ", "is ", "immutable");
        try {
            list.add("Is it ?");
        } catch (UnsupportedOperationException e) {
            System.out.println("List is immutable: ");
        }
        //do not use like this
        List<Integer> listI = Arrays.asList(1,2,3,4,5);
        listI.set(2, 0);
        listI.forEach(System.out::print);
        System.out.println();
        listI.forEach(i -> {
            int j = listI.get(2);
            listI.set(2, j+1);
        });
        listI.forEach(System.out::print);
        System.out.println();
    }
    public static void main(String...args) {
        before9();
        after9();
    }
}
