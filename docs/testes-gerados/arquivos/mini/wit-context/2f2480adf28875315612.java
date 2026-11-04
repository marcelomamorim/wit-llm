/*
 * @test
 * @summary Test Vector(int initialCapacity) constructor for valid and invalid inputs
 * @run main VectorWitupTest
 */

public class VectorWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testInvalidInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with zero initial capacity
        java.util.Vector<Object> v0 = new java.util.Vector<>(0);
        if (v0 == null || v0.size() != 0) {
            throw new AssertionError("Vector with initialCapacity 0 should be created empty");
        }

        // Test with positive initial capacity
        java.util.Vector<String> v10 = new java.util.Vector<>(10);
        if (v10 == null || v10.size() != 0) {
            throw new AssertionError("Vector with initialCapacity 10 should be created empty");
        }
    }

    private static void testInvalidInitialCapacity() {
        // According to WIT expath and source, initialCapacity < 0 should throw IllegalArgumentException
        try {
            new java.util.Vector<Object>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
