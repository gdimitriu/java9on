package functional.multithread.threads;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurentCollectionsDemos {

    public static void main(String...args) {
        ConcurentCollectionsDemos demo = new ConcurentCollectionsDemos();
        demo.testList();
    }

    private void demoListAdd(List<String> list) {
        System.out.println("list: " + list);
        try {
            for(String e : list) {
                System.out.println(e);
                if(!list.contains("Four")) {
                    System.out.println("Calling list.add(Four) ... ");
                    list.add("Four");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private void demoListRemove(List<String> list) {
        System.out.println("list: " + list);
        try {
            for(String e : list) {
                System.out.println(e);
                if(e.equals("Two")) {
                    System.out.println("Calling list.remove(Two) ... ");
                    list.remove("Two");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private void demoListIterRemove(List<String> list) {
        System.out.println("list: " + list);
        try {
            Iterator iter = list.iterator();
            while(iter.hasNext()) {
                String e = (String)iter.next();
                System.out.println(e);
                if(("Two").equals(e)) {
                    System.out.println("Calling iter.remove() ... ");
                    iter.remove();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private void demeRemoveIf(Collection<String> collection) {
        System.out.println("Collection: " + collection);
        System.out.println("Calling list.removeIf( e -> Two.equals(e))...");
        collection.removeIf(e -> "Two".equals(e));
        System.out.println("Collection: " + collection);
    }

    public void testList() {
        System.out.println("***** ArrayList add():");
        demoListAdd(new ArrayList<>(Arrays.asList("One","Two","Three")));
        System.out.println();
        System.out.println("***** CopyOnWriteArrayList add():");
        demoListAdd(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();

        System.out.println("***** ArrayList remove():");
        demoListRemove(new ArrayList<>(Arrays.asList("One","Two","Three")));
        System.out.println();
        System.out.println("***** CopyOnWriteArrayList remove():");
        demoListRemove(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();

        System.out.println("***** ArrayList iterator.remove():");
        demoListIterRemove(new ArrayList<>(Arrays.asList("One","Two","Three")));
        System.out.println();
        System.out.println("***** CopyOnWriteArrayList iterator.remove():");
        demoListIterRemove(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();

        System.out.println("***** ArrayList removeIf():");
        demeRemoveIf(new ArrayList<>(Arrays.asList("One","Two","Three")));
        System.out.println();
        System.out.println("***** CopyOnWriteArrayList removeIf:");
        demeRemoveIf(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three"));
        list.addIfAbsent("Four");
        System.out.println("Collection: " + list);
    }
}
