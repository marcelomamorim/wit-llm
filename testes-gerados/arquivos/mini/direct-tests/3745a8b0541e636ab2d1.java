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
        // Test with matching size
        TestNode node = new TestNode(5);
        node.begin(5L);
        if (node.curSize != 0) {
            throw new AssertionError("curSize should be 0 after begin with matching size");
        }

        // Test with zero size
        TestNode zeroNode = new TestNode(0);
        zeroNode.begin(0L);
        if (zeroNode.curSize != 0) {
            throw new AssertionError("curSize should be 0 after begin with zero size");
        }

        // Test with mismatched size (smaller)
        TestNode mismatchNode1 = new TestNode(3);
        try {
            mismatchNode1.begin(2L);
            throw new AssertionError("Expected IllegalStateException for size mismatch (smaller)");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Begin size 2 is not equal to fixed size 3")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }

        // Test with mismatched size (larger)
        TestNode mismatchNode2 = new TestNode(4);
        try {
            mismatchNode2.begin(10L);
            throw new AssertionError("Expected IllegalStateException for size mismatch (larger)");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Begin size 10 is not equal to fixed size 4")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
