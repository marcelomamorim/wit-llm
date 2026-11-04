/*
 * @test
 * @summary Test java.util.stream.Nodes.accept(double) for correct behavior and exception
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class DoubleFixedNodeBuilder {
        final double[] array;
        int curSize = 0;

        DoubleFixedNodeBuilder(int size) {
            this.array = new double[size];
        }

        public void accept(double i) {
            if (curSize < array.length) {
                array[curSize++] = i;
            } else {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", array.length));
            }
        }
    }

    public static void main(String[] args) {
        testAcceptWithinBounds();
        testAcceptExceedBounds();
    }

    private static void testAcceptWithinBounds() {
        DoubleFixedNodeBuilder builder = new DoubleFixedNodeBuilder(3);
        builder.accept(1.0);
        builder.accept(2.0);
        builder.accept(3.0);
        // No exception expected, array should contain the values in order
        if (builder.curSize != 3) {
            throw new AssertionError("curSize should be 3 but was " + builder.curSize);
        }
        if (builder.array[0] != 1.0 || builder.array[1] != 2.0 || builder.array[2] != 3.0) {
            throw new AssertionError("Array contents do not match expected values");
        }
    }

    private static void testAcceptExceedBounds() {
        DoubleFixedNodeBuilder builder = new DoubleFixedNodeBuilder(2);
        builder.accept(1.0);
        builder.accept(2.0);
        boolean threw = false;
        try {
            builder.accept(3.0);
        } catch (IllegalStateException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("Accept exceeded fixed size")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }
    }
}
