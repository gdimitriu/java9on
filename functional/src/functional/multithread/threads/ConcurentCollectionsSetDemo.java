package functional.multithread.threads;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurentCollectionsSetDemo {
    public static void main(String...args) {
        ConcurentCollectionsSetDemo demo = new ConcurentCollectionsSetDemo();
        demo.testSetRemove();
        demo.testSetAdd();
        demo.testMapPut();
        demo.testMapRemove();
    }

    public void testSetRemove() {
        System.out.println("demoNavigableSetRemove");
        System.out.println("***** TreeSet set.remove(2):");
        demoNavigableSetRemove(new TreeSet<Integer>(Arrays.asList(0,1,2,3)));

        System.out.println();
        System.out.println("***** ConcurentSkipListset set.remove(2):");
        demoNavigableSetRemove(new ConcurrentSkipListSet<>(Arrays.asList(0,1,2,3)));

        System.out.println();
        System.out.println("demoNavigableSetIterRemove");
        System.out.println("***** TreeSet set.remove(2):");
        demoNavigableSetIterRemove(new TreeSet<Integer>(Arrays.asList(0,1,2,3)));

        System.out.println();
        System.out.println("***** ConcurentSkipListset set.remove(2):");
        demoNavigableSetIterRemove(new ConcurrentSkipListSet<>(Arrays.asList(0,1,2,3)));
    }

    private void demoNavigableSetRemove(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            for (int i : set) {
                System.out.println(i);
                if (i == 2) {
                    System.out.println("Calling set.remove(2) ... ");
                    set.remove(2);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    private void demoNavigableSetIterRemove(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            Iterator iter = set.iterator();
            while(iter.hasNext()) {
                Integer e = (Integer) iter.next();
                System.out.println(e);
                if (e == 2) {
                    System.out.println("Calling iter.remove() ...");
                    iter.remove();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    public void testSetAdd() {
        System.out.println("demoNavigableSetAdd");
        System.out.println("***** TreeSet set.add():");
        demoNavigableSetAdd(new TreeSet<Integer>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** ConcurentSkipListset set.add():");
        demoNavigableSetAdd(new ConcurrentSkipListSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
    }

    private void demoNavigableSetAdd(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            int m = set.stream().max(Comparator.naturalOrder()).get() + 1;
            for (int i : set) {
                System.out.println(i);
                System.out.println("Calling set.add(" + m + ")");
                set.add(m++);
                if (m > 6) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    private Map createMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"Zero");
        map.put(1,"One");
        map.put(2, "Two");
        map.put(3, "Three");
        return map;
    }

    private void demoMapPut(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            Set<Integer> keys = map.keySet();
            for (int i : keys) {
                System.out.println(i);
                System.out.println("Calling map.put(8,Eight)...");
                map.put(8, "Eight");

                System.out.println("map: " + map);
                System.out.println("Calling map.put(8,Eight)...");
                map.put(8, "Eight");

                System.out.println("map: " + map);
                System.out.println("Calling map.put(9,Nine)...");
                map.put(9, "Nine");

                System.out.println("map: " + map);
                System.out.println("Calling map.put(9,Nine)...");
                map.put(9, "Nine");

                System.out.println("keys.size(): " + keys.size());
                System.out.println("map: " + map);
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }

    public void testMapPut() {
        System.out.println("***** HashMap map.put():");
        demoMapPut(createMap());

        System.out.println();
        System.out.println("***** ConcurentHashMap map.put():");
        demoMapPut(new ConcurrentHashMap<>(createMap()));

        System.out.println();
        System.out.println("***** ConcurentSkipListMap map.put():");
        demoMapPut(new ConcurrentSkipListMap<>(createMap()));
    }

    public void testMapRemove() {
        System.out.println();
        System.out.println("***** HashMap map.remove(2):");
        demoMapRemove(createMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap map.remove():");
        demoMapRemove(new ConcurrentHashMap(createMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap map.remove():");
        demoMapRemove(new ConcurrentSkipListMap(createMap()));

        System.out.println();
        System.out.println("***** HashMap iter.remove():");
        demoMapIterRemove(createMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap iter.remove():");
        demoMapIterRemove(new ConcurrentHashMap(createMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap iter.remove():");
        demoMapIterRemove(new ConcurrentSkipListMap(createMap()));

        System.out.println();
        System.out.println("***** HashMap map.keySet().remove():");
        demoMapKeySetRemove(createMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap map.keySet().remove():");
        demoMapKeySetRemove(new ConcurrentHashMap(createMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap map.keySet().remove():");
        demoMapKeySetRemove(new ConcurrentSkipListMap(createMap()));
    }

    private void demoMapRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            for (int i : map.keySet()) {
                System.out.println(i);

                System.out.println("Calling map.remove(2)...");
                String result = map.remove(2);
                System.out.println("removed: " + result);

                System.out.println("Calling map.remove(2, Two)...");
                boolean success = map.remove(2, "Two");
                System.out.println("removed: " + success);

                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private void demoMapKeySetRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            for (int i : map.keySet()) {
                System.out.println(i);

                System.out.println("Calling map.keySet().remove(2)...");
                boolean result = map.keySet().remove(2);
                System.out.println("removed: " + result);
                System.out.println("map: " + map);

                System.out.println("Calling map.keySet().removeIf(e -> e == 2)...");
                result = map.keySet().removeIf(e -> e == 2);
                System.out.println("removed: " + result);
                System.out.println("map: " + map);

                System.out.println("Calling map.keySet().removeIf(e -> e == 3)...");
                result = map.keySet().removeIf(e -> e == 3);
                System.out.println("removed: " + result);

                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private void demoMapIterRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                Integer e = (Integer) iter.next();
                System.out.println(e);
                if (e == 2) {
                    System.out.println("Calling iter.remove()...");
                    iter.remove();
                }
                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }
}
