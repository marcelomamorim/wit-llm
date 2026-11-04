/*
 * @test
 * @summary Test Vector constructor with initialCapacity
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity
        Vector<Object> v0 = new Vector<>(0);
        if (v0.size() != 0) {
            throw new RuntimeException("Expected size 0 for initialCapacity 0");
        }

        // Test with positive initial capacity
        Vector<String> v10 = new Vector<>(10);
        if (v10.size() != 0) {
            throw new RuntimeException("Expected size 0 for initialCapacity 10");
        }

        // Test with large initial capacity
        Vector<Integer> v1000 = new Vector<>(1000);
        if (v1000.size() != 0) {
            throw new RuntimeException("Expected size 0 for initialCapacity 1000");
        }

        // Test that adding elements works after construction
        Vector<String> vAdd = new Vector<>(5);
        vAdd.add("test");
        if (!"test".equals(vAdd.get(0))) {
            throw new RuntimeException("Element added not found");
        }

        // Test that negative initial capacity throws IllegalArgumentException
        boolean threw = false;
        try {
            new Vector<Object>(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        }
    }
}
