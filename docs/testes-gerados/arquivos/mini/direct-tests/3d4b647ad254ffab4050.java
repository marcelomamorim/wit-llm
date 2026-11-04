/*
 * @test
 * @summary Test Hashtable constructor with initialCapacity
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        // Test with positive initial capacity
        Hashtable<String, String> ht = new Hashtable<>(10);
        if (ht.size() != 0) {
            throw new RuntimeException("Expected empty hashtable after construction");
        }

        // Test with zero initial capacity
        Hashtable<String, String> htZero = new Hashtable<>(0);
        if (htZero.size() != 0) {
            throw new RuntimeException("Expected empty hashtable after construction with zero capacity");
        }

        // Test with negative initial capacity - expect IllegalArgumentException
        try {
            new Hashtable<String, String>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
