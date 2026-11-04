/*
 * @test
 * @summary Test HashMap constructor with initialCapacity
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity
        HashMap<Object, Object> mapZero = new HashMap<>(0);
        if (mapZero == null) throw new RuntimeException("HashMap constructor returned null");

        // Test with small positive initial capacity
        HashMap<String, String> mapSmall = new HashMap<>(1);
        if (mapSmall == null) throw new RuntimeException("HashMap constructor returned null");

        // Test with typical initial capacity
        HashMap<Integer, Integer> mapTypical = new HashMap<>(16);
        if (mapTypical == null) throw new RuntimeException("HashMap constructor returned null");

        // Test with large initial capacity
        HashMap<Integer, Integer> mapLarge = new HashMap<>(1000);
        if (mapLarge == null) throw new RuntimeException("HashMap constructor returned null");

        // Test with negative initial capacity - expect IllegalArgumentException
        boolean threw = false;
        try {
            new HashMap<>(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
    }
}
