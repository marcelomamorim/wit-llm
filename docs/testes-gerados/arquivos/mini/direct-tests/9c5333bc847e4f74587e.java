/*
 * @test
 * @summary Test WeakHashMap constructor with initial capacity
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity
        WeakHashMap<Object, Object> mapZero = new WeakHashMap<>(0);
        if (mapZero == null) throw new RuntimeException("Constructor returned null");
        if (mapZero.size() != 0) throw new RuntimeException("New map should be empty");

        // Test with small positive initial capacity
        WeakHashMap<Object, Object> mapSmall = new WeakHashMap<>(5);
        if (mapSmall == null) throw new RuntimeException("Constructor returned null");
        if (mapSmall.size() != 0) throw new RuntimeException("New map should be empty");

        // Test with larger initial capacity
        WeakHashMap<Object, Object> mapLarge = new WeakHashMap<>(100);
        if (mapLarge == null) throw new RuntimeException("Constructor returned null");
        if (mapLarge.size() != 0) throw new RuntimeException("New map should be empty");

        // Test with negative initial capacity - expected behavior is not defined in source, so no exception test
        try {
            WeakHashMap<Object, Object> mapNegative = new WeakHashMap<>(-1);
            if (mapNegative == null) throw new RuntimeException("Constructor returned null");
            // No exception expected, but map may behave unexpectedly
        } catch (IllegalArgumentException e) {
            // Acceptable if thrown
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for negative initial capacity", e);
        }
    }
}
