/*
 * @test
 * @summary Test java.util.stream.Nodes.DoubleSpinedNodeBuilder.asPrimitiveArray() for expected behavior and exception
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsPrimitiveArrayNormal();
        testAsPrimitiveArrayExceptionPath();
        System.out.println("PASS");
    }

    private static void testAsPrimitiveArrayNormal() {
        // Create a DoubleSpinedNodeBuilder with a small size to avoid exception
        Nodes.DoubleSpinedNodeBuilder builder = new Nodes.DoubleSpinedNodeBuilder(10);
        // Add some elements
        for (int i = 0; i < 10; i++) {
            builder.accept((double) i);
        }
        // Mark building as false to allow asPrimitiveArray call
        builder.building = false;
        double[] arr = builder.asPrimitiveArray();
        if (arr.length != 10) {
            throw new AssertionError("Expected array length 10 but got " + arr.length);
        }
        for (int i = 0; i < 10; i++) {
            if (arr[i] != (double) i) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + i + " but got " + arr[i]);
            }
        }
    }

    private static void testAsPrimitiveArrayExceptionPath() {
        // Create a DoubleSpinedNodeBuilder with spineIndex == 0 and elementIndex near Integer.MAX_VALUE - 8 to trigger exception
        Nodes.DoubleSpinedNodeBuilder builder = new Nodes.DoubleSpinedNodeBuilder(1);
        // Manually set spineIndex and elementIndex to trigger the exception condition
        builder.spineIndex = 0;
        builder.elementIndex = Integer.MAX_VALUE - 7; // so that Integer.MAX_VALUE - elementIndex <= 8
        builder.building = false;
        try {
            builder.asPrimitiveArray();
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!"Stream size exceeds max array size".equals(msg)) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
