/*
 * @test
 * @summary Basic behavior of ConcurrentHashMap(int initialCapacity) and rejection of negative capacity
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>(16);
        check(m.isEmpty(), "new map with capacity 16 should be empty");
        m.put("a", 1);
        m.put("b", 2);
        check(m.size() == 2, "size should be 2 after two puts");
        check(m.get("a") == 1, "get(a) should be 1");
        check(m.get("b") == 2, "get(b) should be 2");
        check(m.get("missing") == null, "missing key should return null");

        ConcurrentHashMap<String, Integer> zero = new ConcurrentHashMap<>(0);
        check(zero.isEmpty(), "new map with capacity 0 should be empty");
        zero.put("x", 9);
        check(zero.get("x") == 9, "map with capacity 0 should still store entries");

        ConcurrentHashMap<Integer, Integer> big = new ConcurrentHashMap<>(1000);
        for (int i = 0; i < 500; i++) {
            big.put(i, i * 2);
        }
        check(big.size() == 500, "size should be 500 after 500 puts");
        check(big.get(499) == 998, "get(499) should be 998");

        boolean threw = false;
        try {
            new ConcurrentHashMap<String, String>(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "negative initial capacity should throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
