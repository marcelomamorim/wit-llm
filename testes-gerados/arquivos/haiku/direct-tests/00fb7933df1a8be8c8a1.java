/*
 * @test
 * @summary Tests the public ConcurrentHashMap(int initialCapacity, float loadFactor) constructor, which delegates to the three-argument constructor with concurrencyLevel 1
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testBasicUsage();
        testZeroCapacityGrowsCorrectly();
        testIllegalArguments();
    }

    static void testBasicUsage() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>(16, 0.75f);
        check(m.isEmpty(), "new map should be empty");
        check(m.size() == 0, "new map size should be 0");
        check(m.put("a", 1) == null, "first put should return null");
        check(m.put("b", 2) == null, "second put should return null");
        check(m.size() == 2, "size should be 2");
        check(m.get("a") == 1, "get(a) should be 1");
        check(m.get("b") == 2, "get(b) should be 2");
        check(m.get("missing") == null, "missing key should return null");
        check(m.put("a", 10) == 1, "replacing put should return old value");
        check(m.get("a") == 10, "get(a) should reflect replacement");
        check(m.remove("b") == 2, "remove should return old value");
        check(m.size() == 1, "size should be 1 after remove");
    }

    static void testZeroCapacityGrowsCorrectly() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>(0, 0.5f);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            m.put(i, i * 2);
        }
        check(m.size() == n, "size should be " + n + " but was " + m.size());
        for (int i = 0; i < n; i++) {
            Integer v = m.get(i);
            check(v != null && v == i * 2, "wrong value for key " + i);
        }
    }

    static void testIllegalArguments() {
        expectIllegalArgument(-1, 0.75f, "negative initialCapacity");
        expectIllegalArgument(16, 0.0f, "zero loadFactor");
        expectIllegalArgument(16, -1.0f, "negative loadFactor");
    }

    static void expectIllegalArgument(int capacity, float loadFactor, String label) {
        try {
            new ConcurrentHashMap<String, String>(capacity, loadFactor);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + label);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
