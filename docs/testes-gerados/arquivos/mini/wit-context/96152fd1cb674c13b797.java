/*
 * @test
 * @summary Test Vector(int initialCapacity, int capacityIncrement) constructor behavior
 * @run main VectorWitupTest
 */

public class VectorWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNegativeInitialCapacity();
    }

    private static void testValidConstruction() {
        java.util.Vector<Object> v = new java.util.Vector<>(10, 5);
        if (v.size() != 0) {
            throw new AssertionError("New Vector should have size 0");
        }
        // Capacity is not directly accessible, but no exception means success
    }

    private static void testNegativeInitialCapacity() {
        try {
            new java.util.Vector<>(-1, 5);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Illegal Capacity")) {
                throw new AssertionError("Exception message does not contain 'Illegal Capacity'");
            }
        }
    }
}
