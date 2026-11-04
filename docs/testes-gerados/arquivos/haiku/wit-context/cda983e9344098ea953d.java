/*
 * @test
 * @summary Tests HashMap(int initialCapacity) constructor for negative, zero, small and large capacities
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityRejected();
        testMinValueCapacityRejected();
        testZeroCapacityIsUsable();
        testSmallCapacityIsUsable();
        testLargeCapacityConstructs();
        System.out.println("PASS");
    }

    static void testNegativeCapacityRejected() {
        try {
            new HashMap<String, Integer>(-1);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for initialCapacity -1");
    }

    static void testMinValueCapacityRejected() {
        try {
            new HashMap<String, Integer>(Integer.MIN_VALUE);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
    }

    static void testZeroCapacityIsUsable() {
        HashMap<String, Integer> map = new HashMap<>(0);
        if (!map.isEmpty()) {
            throw new RuntimeException("New map with capacity 0 should be empty");
        }
        map.put("a", 1);
        map.put("b", 2);
        if (map.size() != 2 || map.get("a") != 1 || map.get("b") != 2) {
            throw new RuntimeException("Map with capacity 0 did not store mappings correctly");
        }
    }

    static void testSmallCapacityIsUsable() {
        HashMap<String, Integer> map = new HashMap<>(1);
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, i);
        }
        if (map.size() != 100) {
            throw new RuntimeException("Unexpected size: " + map.size());
        }
        for (int i = 0; i < 100; i++) {
            Integer value = map.get("k" + i);
            if (value == null || value != i) {
                throw new RuntimeException("Missing or wrong value for k" + i);
            }
        }
    }

    static void testLargeCapacityConstructs() {
        HashMap<String, Integer> map = new HashMap<>(Integer.MAX_VALUE);
        if (!map.isEmpty() || map.size() != 0) {
            throw new RuntimeException("Map with large capacity should be empty");
        }
    }
}
