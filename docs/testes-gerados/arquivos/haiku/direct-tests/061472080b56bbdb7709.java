/*
 * @test
 * @summary Behavioral tests for LinkedHashMap.forEach: insertion/access order, null action and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testInsertionOrder();
        testAccessOrder();
        testEmptyMap();
        testNullAction();
        testConcurrentModification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("c", 3);
        map.put("a", 1);
        map.put("b", 2);
        map.put("a", 10);
        List<String> keys = new ArrayList<>();
        int[] sum = new int[1];
        map.forEach(e -> {
            keys.add(e.getKey());
            sum[0] += e.getValue();
        });
        check(keys.toString().equals("[c, a, b]"), "unexpected order: " + keys);
        check(sum[0] == 15, "unexpected sum: " + sum[0]);
    }

    static void testAccessOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.get("a");
        List<String> keys = new ArrayList<>();
        map.forEach(e -> keys.add(e.getKey()));
        check(keys.toString().equals("[b, c, a]"), "unexpected access order: " + keys);
    }

    static void testEmptyMap() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int[] calls = new int[1];
        map.forEach(e -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty map");
    }

    static void testNullAction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        try {
            map.forEach(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException not thrown for null action");
    }

    static void testConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.forEach(e -> {
                if (e.getKey().equals("a")) {
                    map.put("z", 26);
                }
            });
        } catch (ConcurrentModificationException expected) {
            return;
        }
        throw new RuntimeException("ConcurrentModificationException not thrown");
    }
}
