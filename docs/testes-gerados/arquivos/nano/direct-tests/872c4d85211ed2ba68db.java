/*
 * @test
 * @summary Test for Nodes.asPrimitiveArray() method.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsPrimitiveArrayReturnsArray();
        testAsPrimitiveArrayWithEmptyNode();
    }

    static void testAsPrimitiveArrayReturnsArray() {
        int[] result = Nodes.asPrimitiveArray();
        if (result == null) {
            throw new AssertionError("Expected non-null array");
        }
        if (result.length != 0) {
            throw new AssertionError("Expected array length 0, but got " + result.length);
        }
    }

    static void testAsPrimitiveArrayWithEmptyNode() {
        int[] result = Nodes.asPrimitiveArray();
        if (result == null) {
            throw new AssertionError("Expected non-null array for empty node");
        }
        if (result.length != 0) {
            throw new AssertionError("Expected array length 0 for empty node, but got " + result.length);
        }
    }
}
