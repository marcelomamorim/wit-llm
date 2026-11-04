/*
 * @test
 * @summary Checks public observable behavior of TreeMap.forEach, including the NullPointerException expath for a null action and ConcurrentModificationException detection.
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsEntriesInSortedOrder();
        testStructuralModificationDuringForEachThrowsCME();
        testValueModificationDoesNotThrow();
    }

    static void testNullActionThrowsNPE() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach((BiConsumer<? super String, ? super Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        TreeMap<String, Integer> map = new TreeMap<>();
        List<String> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(k + "=" + v));
        check(seen.isEmpty(), "action must not be invoked for an empty map");
    }

    static void testVisitsEntriesInSortedOrder() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("c", 3);
        map.put("a", 1);
        map.put("b", 2);
        List<String> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(k + "=" + v));
        check(seen.size() == 3, "expected 3 visited entries but got " + seen.size());
        check(seen.get(0).equals("a=1"), "first entry must be a=1 but was " + seen.get(0));
        check(seen.get(1).equals("b=2"), "second entry must be b=2 but was " + seen.get(1));
        check(seen.get(2).equals("c=3"), "third entry must be c=3 but was " + seen.get(2));
    }

    static void testStructuralModificationDuringForEachThrowsCME() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        boolean thrown = false;
        try {
            map.forEach((k, v) -> map.put(k + "x", v));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "adding a new key during forEach must throw ConcurrentModificationException");
    }

    static void testValueModificationDoesNotThrow() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.forEach((k, v) -> map.put(k, v + 10));
        check(map.get("a").equals(11), "value of a must be 11 but was " + map.get("a"));
        check(map.get("b").equals(12), "value of b must be 12 but was " + map.get("b"));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
