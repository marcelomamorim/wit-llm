/*
 * @test
 * @summary Test java.util.stream.Nodes begin(long) method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class TestNode {
        final Object[] array;
        int curSize;

        TestNode(int fixedSize) {
            this.array = new Object[fixedSize];
            this.curSize = -1;
        }

        public void begin(long size) {
            if (size != array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", size, array.length));
            }
            curSize = 0;
        }
    }

    public static void main(String[] args) {
        // Test 1: begin with size equal to array length sets curSize to 0
        TestNode node1 = new TestNode(5);
        node1.begin(5L);
        if (node1.curSize != 0) {
            throw new AssertionError("curSize should be 0 after begin with matching size");
        }

        // Test 2: begin with size not equal to array length throws IllegalStateException
        TestNode node2 = new TestNode(3);
        try {
            node2.begin(4L);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Begin size 4 is not equal to fixed size 3")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }

        // Test 3: begin with size zero on zero-length array sets curSize to 0
        TestNode node3 = new TestNode(0);
        node3.begin(0L);
        if (node3.curSize != 0) {
            throw new AssertionError("curSize should be 0 after begin with zero size");
        }

        System.out.println("All tests passed.");
    }
}
