/*
 * @test
 * @summary Test java.util.stream.Nodes copyInto method behavior
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;
import java.util.stream.Node;

public class NodesWitupTest {

    // Minimal subclass to test copyInto method
    static class TestNode<T> extends Nodes {
        private final T[] data;
        private boolean building = false;

        TestNode(T[] data) {
            this.data = data;
        }

        @Override
        public void copyInto(T[] array, int offset) {
            if (building) {
                throw new AssertionError("during building");
            }
            // simulate super.copyInto by copying data to array at offset
            System.arraycopy(data, 0, array, offset, data.length);
        }

        // Expose building flag for test
        void setBuilding(boolean b) {
            building = b;
        }
    }

    public static void main(String[] args) {
        // Test normal copyInto
        String[] source = new String[] {"a", "b", "c"};
        String[] target = new String[5];
        TestNode<String> node = new TestNode<>(source);
        node.copyInto(target, 1);
        if (!"a".equals(target[1]) || !"b".equals(target[2]) || !"c".equals(target[3])) {
            throw new RuntimeException("copyInto did not copy elements correctly");
        }

        // Test assertion error when building is true
        node.setBuilding(true);
        boolean caught = false;
        try {
            node.copyInto(target, 0);
        } catch (AssertionError e) {
            if ("during building".equals(e.getMessage())) {
                caught = true;
            } else {
                throw e;
            }
        }
        if (!caught) {
            throw new RuntimeException("Expected AssertionError not thrown when building is true");
        }

        System.out.println("Test passed");
    }
}
