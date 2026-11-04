/*
 * @test
 * @summary Validates observable behavior of the public Hashtable(int initialCapacity) constructor
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testZeroCapacityIsUsable();
        testSmallCapacityGrowsAndKeepsEntries();
        testNegativeCapacityRejected();
        testNullKeyRejected();
        testNullValueRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroCapacityIsUsable() {
        Hashtable<String, Integer> h = new Hashtable<>(0);
        check(h.isEmpty(), "new Hashtable(0) should be empty");
        h.put("a", 1);
        check(h.size() == 1, "size should be 1 after one put");
        check(Integer.valueOf(1).equals(h.get("a")), "get(a) should return 1");
    }

    static void testSmallCapacityGrowsAndKeepsEntries() {
        Hashtable<Integer, Integer> h = new Hashtable<>(1);
        for (int i = 0; i < 1000; i++) {
            h.put(i, i * 2);
        }
        check(h.size() == 1000, "size should be 1000 but was " + h.size());
        check(Integer.valueOf(1000).equals(h.get(500)), "get(500) should return 1000");
        check(h.containsKey(999), "containsKey(999) should be true");
        check(!h.containsKey(1000), "containsKey(1000) should be false");
    }

    static void testNegativeCapacityRejected() {
        try {
            new Hashtable<String, String>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullKeyRejected() {
        Hashtable<String, String> h = new Hashtable<>(4);
        try {
            h.put(null, "x");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullValueRejected() {
        Hashtable<String, String> h = new Hashtable<>(4);
        try {
            h.put("k", null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
