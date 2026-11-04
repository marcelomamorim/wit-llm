/*
 * @test
 * @summary WITUP-derived checks for ConcurrentSkipListMap replace(K, V, V) and its SubMap null-key path
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(5, "e");

        check(map.replace(1, "a", "x"), "replace with matching old value should return true");
        check("x".equals(map.get(1)), "value should be updated after successful replace");

        check(!map.replace(2, "zz", "y"), "replace with non-matching old value should return false");
        check("b".equals(map.get(2)), "value should be unchanged after failed replace");

        check(!map.replace(9, "a", "b"), "replace on absent key should return false");
        check(!map.containsKey(9), "replace on absent key must not insert the key");

        expectNpe(() -> map.replace(null, "b", "c"), "null key on top-level map must throw NullPointerException");

        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, 4);
        check(sub.replace(2, "b", "q"), "replace through subMap view with matching value should return true");
        check("q".equals(map.get(2)), "subMap replace should be visible in backing map");

        expectNpe(() -> sub.replace(null, "b", "c"), "null key through subMap view must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectNpe(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException(message);
    }
}
