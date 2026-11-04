/*
 * @test
 * @summary WITUP-derived tests for ConcurrentSkipListMap.forEach
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIterationOrderAndValues();
        testEmptyMapInvokesNoAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testIterationOrderAndValues() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("c", 3);
        map.put("a", 1);
        map.put("b", 2);
        List<String> visited = new ArrayList<>();
        map.forEach((k, v) -> visited.add(k + "=" + v));
        check(visited.size() == 3, "expected 3 entries, got " + visited.size());
        check(visited.get(0).equals("a=1"), "first entry should be a=1, got " + visited.get(0));
        check(visited.get(1).equals("b=2"), "second entry should be b=2, got " + visited.get(1));
        check(visited.get(2).equals("c=3"), "third entry should be c=3, got " + visited.get(2));
    }

    static void testEmptyMapInvokesNoAction() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        int[] calls = new int[1];
        map.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }
}
