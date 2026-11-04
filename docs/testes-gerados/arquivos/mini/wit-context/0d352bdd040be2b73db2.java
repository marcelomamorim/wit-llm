/*
 * @test
 * @summary Test default constructor of WeakHashMap for valid initialization and exception safety
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        // Test default constructor creates a non-null instance
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        if (map == null) {
            throw new AssertionError("WeakHashMap default constructor returned null");
        }

        // The map should be empty initially
        if (map.size() != 0) {
            throw new AssertionError("New WeakHashMap is not empty");
        }

        // The map should accept null key and null value without throwing
        map.put(null, null);
        if (!map.containsKey(null)) {
            throw new AssertionError("WeakHashMap should contain null key after put");
        }
        if (map.get(null) != null) {
            throw new AssertionError("WeakHashMap should return null value for null key");
        }

        // No exceptions expected from default constructor under normal conditions
        // The WIT expaths suggesting IllegalArgumentException depend on invalid static constants
        // which are not modifiable here, so no test for exception is added.

        System.out.println("WeakHashMap default constructor tests passed");
    }
}
