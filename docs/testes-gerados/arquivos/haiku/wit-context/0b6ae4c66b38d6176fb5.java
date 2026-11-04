/*
 * @test
 * @summary Verifies IdentityHashMap(int) rejects negative expected sizes and accepts non-negative ones
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testNegativeRejected(-1);
        testNegativeRejected(Integer.MIN_VALUE);
        testAccepted(0);
        testAccepted(1);
        testAccepted(1000);
    }

    static void testNegativeRejected(int expectedMaxSize) {
        try {
            new IdentityHashMap<String, String>(expectedMaxSize);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + expectedMaxSize);
    }

    static void testAccepted(int expectedMaxSize) {
        IdentityHashMap<String, String> map = new IdentityHashMap<>(expectedMaxSize);
        if (!map.isEmpty() || map.size() != 0) {
            throw new RuntimeException("New map should be empty for " + expectedMaxSize);
        }
        String k1 = new String("k");
        String k2 = new String("k");
        map.put(k1, "a");
        map.put(k2, "b");
        if (map.size() != 2) {
            throw new RuntimeException("Expected size 2 for " + expectedMaxSize);
        }
        if (!"a".equals(map.get(k1)) || !"b".equals(map.get(k2))) {
            throw new RuntimeException("Identity lookup failed for " + expectedMaxSize);
        }
        if (map.get(new String("k")) != null) {
            throw new RuntimeException("Equal but non-identical key must not match for " + expectedMaxSize);
        }
    }
}
