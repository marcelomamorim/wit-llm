/*
 * @test
 * @summary Test Vector constructor with initialCapacity and capacityIncrement
 * @run main VectorWitupTest
 */

public class VectorWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidConstructor() {
        java.util.Vector<Object> v = new java.util.Vector<>(5, 3);
        if (v.size() != 0) {
            throw new RuntimeException("Expected size 0 but got " + v.size());
        }
    }

    private static void testZeroInitialCapacity() {
        java.util.Vector<Object> v = new java.util.Vector<>(0, 1);
        if (v.size() != 0) {
            throw new RuntimeException("Expected size 0 but got " + v.size());
        }
    }

    private static void testNegativeInitialCapacity() {
        try {
            new java.util.Vector<>(-1, 1);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
