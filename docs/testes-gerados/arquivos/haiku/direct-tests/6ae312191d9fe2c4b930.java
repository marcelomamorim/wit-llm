/*
 * @test
 * @summary ConcurrentHashMap.replace(K,V) null checks and replacement semantics
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    interface Action {
        void run();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    static void expectNpe(Action action, String message) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + message);
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        check(map.replace("a", 1) == null, "replace on absent key returns null");
        check(!map.containsKey("a"), "replace on absent key must not insert");
        check(map.isEmpty(), "map must remain empty");

        map.put("a", 10);
        check(map.replace("a", 20) == 10, "replace returns previous value");
        check(map.get("a") == 20, "replace updates the mapping");
        check(map.size() == 1, "replace does not change size");

        expectNpe(() -> map.replace(null, 1), "null key");
        expectNpe(() -> map.replace("a", null), "null value");
        expectNpe(() -> map.replace(null, null), "null key and value");
        check(map.get("a") == 20, "failed replace must not change mapping");
        check(map.size() == 1, "failed replace must not change size");

        ConcurrentHashMap<String, Integer> copy = new ConcurrentHashMap<>(map);
        check(copy.replace("a", 30) == 20, "copy replace returns previous value");
        check(copy.get("a") == 30, "copy replace updates value");
        check(map.get("a") == 20, "replace on copy must not affect original");
    }
}
