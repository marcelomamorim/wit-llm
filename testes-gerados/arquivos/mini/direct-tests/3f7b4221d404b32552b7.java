/*
 * @test
 * @summary Test java.util.stream.Nodes begin(long) method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class DummyNode {
        final long length;
        final long offset = 0;
        int index = -1;
        int fence = -1;

        DummyNode(long length) {
            this.length = length;
        }

        public void begin(long size) {
            if (size > length)
                throw new IllegalStateException("size passed to Sink.begin exceeds array length");
            index = (int) offset;
            fence = index + (int) length;
        }
    }

    public static void main(String[] args) {
        // Test with size less than length - should set index and fence correctly
        DummyNode node1 = new DummyNode(10L);
        node1.begin(5L);
        if (node1.index != 0 || node1.fence != 10) {
            throw new Error("Test failed: index or fence not set correctly for size < length");
        }

        // Test with size equal to length - should set index and fence correctly
        DummyNode node2 = new DummyNode(7L);
        node2.begin(7L);
        if (node2.index != 0 || node2.fence != 7) {
            throw new Error("Test failed: index or fence not set correctly for size == length");
        }

        // Test with size greater than length - should throw IllegalStateException
        DummyNode node3 = new DummyNode(3L);
        boolean threw = false;
        try {
            node3.begin(4L);
        } catch (IllegalStateException e) {
            threw = true;
            if (!"size passed to Sink.begin exceeds array length".equals(e.getMessage())) {
                throw new Error("Test failed: exception message mismatch");
            }
        }
        if (!threw) {
            throw new Error("Test failed: expected IllegalStateException not thrown");
        }
    }
}
