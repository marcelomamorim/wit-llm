/*
 * @test
 * @summary Witup-generated checks for ConcurrentHashMap.put null handling and return values
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNpe();
        testNullValueThrowsNpe();
        testFailedPutDoesNotChangeMap();
        testPutReturnsPreviousValue();
        testPutFirstReturnsNull();
    }

    static void expectNpe(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError(message);
    }

    static void testNullKeyThrowsNpe() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        expectNpe(() -> map.put(null, "v"), "put(null, value) must throw NullPointerException");
    }

    static void testNullValueThrowsNpe() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        expectNpe(() -> map.put("k", null), "put(key, null) must throw NullPointerException");
    }

    static void testFailedPutDoesNotChangeMap() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        expectNpe(() -> map.put(null, "x"), "put(null, value) must throw NullPointerException");
        expectNpe(() -> map.put("b", null), "put(key, null) must throw NullPointerException");
        if (map.size() != 1) {
            throw new AssertionError("size changed after rejected put: " + map.size());
        }
        if (!"1".equals(map.get("a"))) {
            throw new AssertionError("existing mapping changed after rejected put");
        }
        if (map.containsKey("b")) {
            throw new AssertionError("rejected put must not add a mapping");
        }
    }

    static void testPutReturnsPreviousValue() {
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        if (map.put("a", "1") != null) {
            throw new AssertionError("first put must return null");
        }
        if (!"1".equals(map.put("a", "2"))) {
            throw new AssertionError("second put must return previous value");
        }
        if (!"2".equals(map.get("a"))) {
            throw new AssertionError("get must return the most recent value");
        }
    }

    static void testPutFirstReturnsNull() {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        if (map.put(1, 10) != null) {
            throw new AssertionError("put of new key must return null");
        }
        if (map.size() != 1) {
            throw new AssertionError("size must be 1 after one put");
        }
    }
}
