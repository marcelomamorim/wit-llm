/*
 * @test
 * @summary Witup-derived tests for Hashtable(int, float) constructor argument validation
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityRejected();
        testNonPositiveLoadFactorRejected();
        testNaNLoadFactorRejected();
        testZeroCapacityIsUsable();
        testValidParametersAreUsable();
        System.out.println("PASS");
    }

    static void testNegativeCapacityRejected() {
        expectIllegalArgument(-1, 0.75f);
        expectIllegalArgument(Integer.MIN_VALUE, 0.75f);
    }

    static void testNonPositiveLoadFactorRejected() {
        expectIllegalArgument(10, 0.0f);
        expectIllegalArgument(10, -0.5f);
        expectIllegalArgument(0, 0.0f);
    }

    static void testNaNLoadFactorRejected() {
        expectIllegalArgument(10, Float.NaN);
    }

    static void testZeroCapacityIsUsable() {
        Hashtable<String, String> h = new Hashtable<String, String>(0, 0.75f);
        check(h.isEmpty(), "new table with capacity 0 should be empty");
        h.put("k", "v");
        check("v".equals(h.get("k")), "put/get should work with capacity 0");
        check(h.size() == 1, "size should be 1 after one put");
    }

    static void testValidParametersAreUsable() {
        Hashtable<String, Integer> h = new Hashtable<String, Integer>(16, 0.5f);
        check(h.isEmpty(), "new table should be empty");
        for (int i = 0; i < 100; i++) {
            h.put("key" + i, i);
        }
        check(h.size() == 100, "size should be 100, was " + h.size());
        for (int i = 0; i < 100; i++) {
            check(h.get("key" + i) == i, "wrong value for key" + i);
        }
    }

    static void expectIllegalArgument(int capacity, float loadFactor) {
        try {
            new Hashtable<String, String>(capacity, loadFactor);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for capacity="
                + capacity + " loadFactor=" + loadFactor);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
