/*
 * @test
 * @summary Deterministic observable-behavior tests for ConcurrentHashMap.put
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testFirstInsertReturnsNull();
        testReplaceReturnsPreviousValue();
        testPutWithExplicitConcurrencyConstructor();
        testPutCopiedFromMap();
        testManyInsertsAcrossResize();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFirstInsertReturnsNull() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Integer previous = map.put("a", 1);
        check(previous == null, "first put must return null, got " + previous);
        check(map.get("a") == 1, "value not stored after first put");
        check(map.size() == 1, "size must be 1 after first put, got " + map.size());
    }

    static void testReplaceReturnsPreviousValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Integer previous = map.put("a", 2);
        check(previous != null && previous == 1, "replace must return old value 1, got " + previous);
        check(map.get("a") == 2, "value must be updated to 2");
        check(map.size() == 1, "size must remain 1 after replace, got " + map.size());
    }

    static void testPutWithExplicitConcurrencyConstructor() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(16, 0.75f, 1);
        check(map.put(7, "x") == null, "first put must return null");
        check("x".equals(map.put(7, "y")), "replace must return previous value \"x\"");
        check("y".equals(map.get(7)), "get must return \"y\"");
    }

    static void testPutCopiedFromMap() {
        Map<String, Integer> source = new HashMap<>();
        source.put("k", 10);
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(source);
        check(map.put("k", 20) == 10, "put on copied key must return 10");
        check(map.put("n", 30) == null, "put on new key must return null");
        check(map.size() == 2, "size must be 2, got " + map.size());
    }

    static void testManyInsertsAcrossResize() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(1);
        final int n = 1000;
        for (int i = 0; i < n; i++) {
            check(map.put(i, i * 2) == null, "new key " + i + " must return null");
        }
        check(map.size() == n, "size must be " + n + ", got " + map.size());
        for (int i = 0; i < n; i++) {
            check(map.get(i) == i * 2, "wrong value for key " + i);
        }
        for (int i = 0; i < n; i++) {
            check(map.put(i, -i) == i * 2, "replace must return previous value for key " + i);
        }
        check(map.size() == n, "size must remain " + n + " after replacements");
    }
}
