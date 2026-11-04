/*
 * @test
 * @summary Witup-derived checks for HashMap(int initialCapacity, float loadFactor) exception paths and valid construction
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNegativeInitialCapacityThrows();
        testMinIntInitialCapacityThrows();
        testZeroLoadFactorThrows();
        testNegativeLoadFactorThrows();
        testNaNLoadFactorThrows();
        testZeroCapacityIsAccepted();
        testLargeCapacityIsClampedAndUsable();
    }

    private static void testNegativeInitialCapacityThrows() {
        expectIllegalArgument(() -> new HashMap<String, String>(-1, 0.75f), "initialCapacity -1");
    }

    private static void testMinIntInitialCapacityThrows() {
        expectIllegalArgument(() -> new HashMap<String, String>(Integer.MIN_VALUE, 0.75f), "initialCapacity MIN_VALUE");
    }

    private static void testZeroLoadFactorThrows() {
        expectIllegalArgument(() -> new HashMap<String, String>(16, 0.0f), "loadFactor 0.0");
    }

    private static void testNegativeLoadFactorThrows() {
        expectIllegalArgument(() -> new HashMap<String, String>(16, -0.5f), "loadFactor -0.5");
    }

    private static void testNaNLoadFactorThrows() {
        expectIllegalArgument(() -> new HashMap<String, String>(16, Float.NaN), "loadFactor NaN");
    }

    private static void testZeroCapacityIsAccepted() {
        HashMap<String, Integer> map = new HashMap<>(0, 0.75f);
        check(map.isEmpty(), "new map with capacity 0 should be empty");
        map.put("a", 1);
        check(map.get("a") == 1, "put/get should work with initial capacity 0");
        check(map.size() == 1, "size should be 1 after one put");
    }

    private static void testLargeCapacityIsClampedAndUsable() {
        HashMap<String, Integer> map = new HashMap<>(Integer.MAX_VALUE, 0.75f);
        check(map.isEmpty(), "new map with huge capacity should be empty");
        check(map.size() == 0, "size of new map should be 0");
        check(map.get("missing") == null, "get on empty map should return null");
    }

    private interface Action {
        void run();
    }

    private static void expectIllegalArgument(Action action, String description) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + description);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
