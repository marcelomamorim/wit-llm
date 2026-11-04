/*
 * @test
 * @summary Test for Nodes.asPrimitiveArray() method to ensure it returns a double array.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsPrimitiveArrayReturnsArray();
    }

    public static void testAsPrimitiveArrayReturnsArray() {
        double[] result = Nodes.asPrimitiveArray();
        if (result == null) {
            throw new AssertionError("Expected non-null array");
        }
        if (result.getClass() != double[].class) {
            throw new AssertionError("Expected array of type double[]");
        }
        // Additional check: array length should be zero or positive
        if (result.length < 0) {
            throw new AssertionError("Array length should be non-negative");
        }
    }
}
