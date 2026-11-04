/*
 * @test
 * @summary Tests argument validation and observable behavior of HashMap(int initialCapacity, float loadFactor)
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityRejected();
        testNonPositiveLoadFactorRejected();
        testNaNLoadFactorRejected();
        testZeroCapacityStillUsable();
        testOversizedCapacityAccepted();
        testCustomLoadFactorBehavior();
    }

    static void expectIllegalArgument(int capacity, float loadFactor) {
        try {
            new HashMap<String, String>(capacity, loadFactor);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for capacity="
                + capacity + ", loadFactor=" + loadFactor);
    }

    static void testNegativeCapacityRejected() {
        expectIllegalArgument(-1, 0.75f);
        expectIllegalArgument(Integer.MIN_VALUE, 0.75f);
    }

    static void testNonPositiveLoadFactorRejected() {
        expectIllegalArgument(16, 0.0f);
        expectIllegalArgument(16, -0.5f);
    }

    static void testNaNLoadFactorRejected() {
        expectIllegalArgument(16, Float.NaN);
    }

    static void testZeroCapacityStillUsable() {
        HashMap<String, Integer> map = new HashMap<>(0, 0.75f);
        map.put("a", 1);
        map.put("b", 2);
        if (map.get("a") != 1 || map.get("b") != 2 || map.size() != 2) {
            throw new AssertionError("Map with initial capacity 0 does not behave correctly");
        }
    }

    static void testOversizedCapacityAccepted() {
        HashMap<Integer, Integer> map = new HashMap<>(Integer.MAX_VALUE, 0.75f);
        if (!map.isEmpty() || map.size() != 0) {
            throw new AssertionError("Newly created map should be empty");
        }
    }

    static void testCustomLoadFactorBehavior() {
        HashMap<Integer, String> map = new HashMap<>(4, 0.5f);
        for (int i = 0; i < 50; i++) {
            map.put(i, "v" + i);
        }
        if (map.size() != 50) {
            throw new AssertionError("Unexpected size: " + map.size());
        }
        for (int i = 0; i < 50; i++) {
            if (!("v" + i).equals(map.get(i))) {
                throw new AssertionError("Wrong value for key " + i);
            }
        }
    }
}
