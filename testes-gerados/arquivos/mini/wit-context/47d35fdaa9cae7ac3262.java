/*
 * @test
 * @summary Test java.util.stream.Nodes.begin(long) method for IllegalStateException on size mismatch
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class DoubleFixedNodeBuilder {
        final double[] array;
        int curSize;

        DoubleFixedNodeBuilder(double[] array) {
            this.array = array;
            this.curSize = 0;
        }

        public void begin(long size) {
            if (size != array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", size, array.length));
            }
            curSize = 0;
        }
    }

    public static void main(String[] args) {
        testBeginWithMatchingSize();
        testBeginWithMismatchedSize();
    }

    private static void testBeginWithMatchingSize() {
        double[] arr = new double[5];
        DoubleFixedNodeBuilder builder = new DoubleFixedNodeBuilder(arr);
        try {
            builder.begin(5L);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for matching size", e);
        }
        if (builder.curSize != 0) {
            throw new RuntimeException("curSize should be reset to 0 after begin");
        }
    }

    private static void testBeginWithMismatchedSize() {
        double[] arr = new double[5];
        DoubleFixedNodeBuilder builder = new DoubleFixedNodeBuilder(arr);
        boolean thrown = false;
        try {
            builder.begin(4L);
        } catch (IllegalStateException e) {
            thrown = true;
            String msg = e.getMessage();
            if (!msg.contains("Begin size 4 is not equal to fixed size 5")) {
                throw new RuntimeException("Exception message mismatch: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException was not thrown");
        }
    }
}
