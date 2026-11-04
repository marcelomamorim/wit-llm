/*
 * @test
 * @summary Test for Nodes.asPrimitiveArray() method, focusing on normal behavior.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsPrimitiveArrayReturnsArray();
    }

    public static void testAsPrimitiveArrayReturnsArray() {
        try {
            int[] result = Nodes.asPrimitiveArray();
            if (result != null && result.getClass().isArray() && result instanceof int[]) {
                System.out.println("PASS: asPrimitiveArray() returned an int[] array.");
            } else {
                System.out.println("FAIL: asPrimitiveArray() did not return an int[] array.");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception thrown during asPrimitiveArray(): " + t);
        }
    }
}
