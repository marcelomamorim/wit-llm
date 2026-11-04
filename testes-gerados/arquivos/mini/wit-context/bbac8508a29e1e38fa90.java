/*
 * @test
 * @summary Test ConcurrentHashMap(int initialCapacity) constructor for valid and invalid inputs
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with a valid initial capacity (e.g., 16)
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>(16);
        if (map == null) {
            throw new AssertionError("Constructor returned null for valid initial capacity");
        }
    }

    private static void testNegativeInitialCapacity() {
        // According to WIT expaths, negative initialCapacity should throw IllegalArgumentException
        // The constructor calls this(initialCapacity, LOAD_FACTOR, 1), so concurrencyLevel=1 is valid
        try {
            new ConcurrentHashMap<Object, Object>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
