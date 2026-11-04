/*
 * @test
 * @summary Tests LinkedHashMap.forEach null action, insertion order and concurrent modification detection
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testForEachVisitsInInsertionOrder();
        testForEachOnEmptyMapDoesNotInvokeAction();
        testStructuralModificationDuringForEachThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach((BiConsumer<? super String, ? super Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testForEachVisitsInInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("b", 1);
        map.put("a", 2);
        map.put("c", 3);
        map.put("b", 4);
        final List<String> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(k + "=" + v));
        check(seen.size() == 3, "expected 3 entries, got " + seen.size());
        check(seen.get(0).equals("b=4"), "first entry wrong: " + seen.get(0));
        check(seen.get(1).equals("a=2"), "second entry wrong: " + seen.get(1));
        check(seen.get(2).equals("c=3"), "third entry wrong: " + seen.get(2));
    }

    static void testForEachOnEmptyMapDoesNotInvokeAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        final int[] calls = new int[1];
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testStructuralModificationDuringForEachThrowsConcurrentModificationException() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        boolean thrown = false;
        try {
            map.forEach((k, v) -> {
                if ("a".equals(k)) {
                    map.put("z", 26);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }
}
