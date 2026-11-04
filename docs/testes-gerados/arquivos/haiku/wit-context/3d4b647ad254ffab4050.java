/*
 * @test
 * @summary Verifies Hashtable(int) rejects negative initial capacity and accepts zero and positive capacities
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityRejected(-1);
        testNegativeCapacityRejected(Integer.MIN_VALUE);
        testZeroCapacityIsUsable();
        testPositiveCapacityIsUsable();
    }

    static void testNegativeCapacityRejected(int initialCapacity) {
        try {
            new Hashtable<String, Integer>(initialCapacity);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for initialCapacity=" + initialCapacity);
    }

    static void testZeroCapacityIsUsable() {
        Hashtable<String, Integer> table = new Hashtable<>(0);
        table.put("one", 1);
        table.put("two", 2);
        check(table.size() == 2, "size after two puts with capacity 0");
        check(table.get("one") == 1, "get(one) with capacity 0");
        check(table.get("two") == 2, "get(two) with capacity 0");
    }

    static void testPositiveCapacityIsUsable() {
        Hashtable<String, Integer> table = new Hashtable<>(16);
        for (int i = 0; i < 100; i++) {
            table.put("k" + i, i);
        }
        check(table.size() == 100, "size after 100 puts with capacity 16");
        for (int i = 0; i < 100; i++) {
            check(table.get("k" + i) == i, "get(k" + i + ") with capacity 16");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
