/*
 * @test
 * @summary Test java.util.stream.Nodes.accept(int) method behavior
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class TestNode extends Nodes {
        final int[] array;
        int index = 0;
        final int fence;

        TestNode(int size) {
            this.array = new int[size];
            this.fence = size;
        }

        public void accept(int value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }
    }

    public static void main(String[] args) {
        // Test normal accept calls
        TestNode node = new TestNode(3);
        node.accept(10);
        node.accept(20);
        node.accept(30);

        if (node.array[0] != 10) throw new Error("Expected 10 at index 0");
        if (node.array[1] != 20) throw new Error("Expected 20 at index 1");
        if (node.array[2] != 30) throw new Error("Expected 30 at index 2");

        // Test accept throws IndexOutOfBoundsException when index >= fence
        boolean thrown = false;
        try {
            node.accept(40); // index is 3, fence is 3
        } catch (IndexOutOfBoundsException e) {
            if (!"3".equals(e.getMessage())) {
                throw new Error("Exception message mismatch: " + e.getMessage());
            }
            thrown = true;
        }
        if (!thrown) throw new Error("Expected IndexOutOfBoundsException not thrown");
    }
}
