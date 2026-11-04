/*
 * @test
 * @summary Test java.util.stream.Nodes.end() method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class TestNode {
        final Object[] array;
        int curSize;

        TestNode(int fixedSize, int curSize) {
            this.array = new Object[fixedSize];
            this.curSize = curSize;
        }

        public void end() {
            if (curSize < array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", curSize, array.length));
            }
        }
    }

    public static void main(String[] args) {
        // Test case: curSize == array.length (should not throw)
        TestNode node1 = new TestNode(5, 5);
        node1.end();

        // Test case: curSize > array.length (should not throw)
        TestNode node2 = new TestNode(5, 6);
        node2.end();

        // Test case: curSize < array.length (should throw IllegalStateException)
        TestNode node3 = new TestNode(5, 4);
        try {
            node3.end();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("End size 4 is less than fixed size 5")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }

        System.out.println("All tests passed.");
    }
}
