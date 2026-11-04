/*
 * @test
 * @summary Verify ConcurrentHashMap values view forEach(Consumer): null action throws NullPointerException, empty map performs no calls, and all values are visited
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyMapThrowsNPE();
        testEmptyMapNoCalls();
        testVisitsAllValues();
        testSingleValueVisitedOnce();
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Consumer<? super Integer> nullAction = null;
        boolean thrown = false;
        try {
            map.values().forEach(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on non-empty map must throw NullPointerException");
    }

    static void testNullActionOnEmptyMapThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Consumer<? super Integer> nullAction = null;
        boolean thrown = false;
        try {
            map.values().forEach(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on empty map must throw NullPointerException");
    }

    static void testEmptyMapNoCalls() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        int[] calls = new int[1];
        map.values().forEach(v -> calls[0]++);
        check(calls[0] == 0, "forEach on empty map must not invoke action, got " + calls[0]);
    }

    static void testVisitsAllValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put("k" + i, i);
        }
        int[] count = new int[1];
        long[] sum = new long[1];
        map.values().forEach(v -> {
            count[0]++;
            sum[0] += v;
        });
        check(count[0] == 5, "expected 5 visits, got " + count[0]);
        check(sum[0] == 15L, "expected sum 15, got " + sum[0]);
    }

    static void testSingleValueVisitedOnce() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("only", "value");
        int[] count = new int[1];
        String[] seen = new String[1];
        map.values().forEach(v -> {
            count[0]++;
            seen[0] = v;
        });
        check(count[0] == 1, "expected exactly 1 visit, got " + count[0]);
        check("value".equals(seen[0]), "expected value 'value', got " + seen[0]);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
