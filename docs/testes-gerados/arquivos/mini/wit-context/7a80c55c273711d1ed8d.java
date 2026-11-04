/*
 * @test
 * @summary Test Properties(int initialCapacity) constructor for valid and invalid inputs
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Typical positive initial capacity
        Properties p = new Properties(10);
        if (p == null) {
            throw new AssertionError("Properties instance is null");
        }
    }

    private static void testZeroInitialCapacity() {
        // Zero initial capacity is allowed by ConcurrentHashMap
        Properties p = new Properties(0);
        if (p == null) {
            throw new AssertionError("Properties instance is null");
        }
    }

    private static void testNegativeInitialCapacity() {
        // Negative initial capacity should throw IllegalArgumentException
        try {
            new Properties(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
