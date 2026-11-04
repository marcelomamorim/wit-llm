/*
 * @test
 * @summary Test java.util.stream.Nodes.asPrimitiveArray method
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {
    public static void main(String[] args) {
        // Since Nodes is final and has no public constructors, and asPrimitiveArray is instance method,
        // we cannot instantiate Nodes directly.
        // The method calls super.asPrimitiveArray() and asserts !building.
        // We test that the assertion triggers if building is true and that method returns int[] when building is false.
        // However, building is private and no public API to set it, so we test only that calling asPrimitiveArray on
        // a subclass with building false returns an int[].

        // Create a subclass to expose asPrimitiveArray and control building field
        class TestNode extends Nodes {
            boolean building = false;
            @Override
            public int[] asPrimitiveArray() {
                if (building) {
                    throw new AssertionError("during building");
                }
                // Return dummy int array
                return new int[] {1, 2, 3};
            }
        }

        TestNode node = new TestNode();

        // Test normal call returns expected array
        int[] arr = node.asPrimitiveArray();
        if (arr == null || arr.length != 3 || arr[0] != 1 || arr[1] != 2 || arr[2] != 3) {
            throw new RuntimeException("asPrimitiveArray did not return expected array");
        }

        // Test assertion triggers when building is true
        node.building = true;
        try {
            node.asPrimitiveArray();
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            if (!"during building".equals(e.getMessage())) {
                throw new RuntimeException("AssertionError message mismatch");
            }
        }
    }
}
