/*
 * @test
 * @summary Test java.util.Hashtable(int initialCapacity) constructor for valid and invalid inputs
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with zero initial capacity (valid)
        Hashtable<Object, Object> htZero = new Hashtable<>(0);
        if (htZero == null) {
            throw new AssertionError("Hashtable constructor returned null for initialCapacity=0");
        }

        // Test with positive initial capacity
        Hashtable<Object, Object> htPositive = new Hashtable<>(10);
        if (htPositive == null) {
            throw new AssertionError("Hashtable constructor returned null for initialCapacity=10");
        }
    }

    private static void testNegativeInitialCapacity() {
        try {
            new Hashtable<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
