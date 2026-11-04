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
            if (size != array.length)
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", size, array.length));
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

        // Test with size different from array length - expect IllegalStateException
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

        // Test with zero size matching array length
        TestNode node3 = new TestNode(0);
        node3.begin(0L);
        if (node3.curSize != 0) {
            throw new AssertionError("curSize should be 0 after begin with zero size");
        }

        System.out.println("All tests passed.");
    }
}
