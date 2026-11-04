/*
 * @test
 * @summary Verifies that KeySpliterator.tryAdvance (reached via ConcurrentHashMap.keySet().spliterator()) rejects a null action with NullPointerException and behaves correctly for empty and populated maps
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionThrowsNPEAfterExhaustion();
        testEmptyMapReturnsFalse();
        testAdvanceVisitsAllKeys();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Spliterator<String> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) on a non-empty key spliterator must throw NullPointerException");
    }

    static void testNullActionThrowsNPEAfterExhaustion() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 10);
        Spliterator<String> sp = map.keySet().spliterator();
        Consumer<String> sink = k -> { };
        while (sp.tryAdvance(sink)) {
            // drain the spliterator
        }
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) on an exhausted key spliterator must still throw NullPointerException");
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Spliterator<String> sp = map.keySet().spliterator();
        final int[] calls = {0};
        Consumer<String> counter = k -> calls[0]++;
        boolean advanced = sp.tryAdvance(counter);
        check(!advanced, "tryAdvance on an empty map must return false");
        check(calls[0] == 0, "action must not be invoked when there are no keys");
    }

    static void testAdvanceVisitsAllKeys() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        Spliterator<String> sp = map.keySet().spliterator();
        Set<String> seen = new HashSet<>();
        List<String> order = new ArrayList<>();
        Consumer<String> collector = k -> {
            seen.add(k);
            order.add(k);
        };
        int count = 0;
        while (sp.tryAdvance(collector)) {
            count++;
        }
        check(count == 3, "expected 3 advances, got " + count);
        check(seen.size() == 3, "expected 3 distinct keys, got " + seen.size());
        check(seen.contains("one") && seen.contains("two") && seen.contains("three"),
              "all keys must be visited exactly once");
        check(!sp.tryAdvance(collector), "tryAdvance must return false once exhausted");
    }
}
