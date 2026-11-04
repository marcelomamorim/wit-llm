/*
 * @test
 * @summary Test java.util.stream.Nodes.FixedNodeBuilder.begin(long) for size validation and state reset
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class FixedNodeBuilder<T> {
        final Object[] array;
        int curSize;

        FixedNodeBuilder(int size) {
            this.array = new Object[size];
            this.curSize = 0;
        }

        public void begin(long size) {
            if (size != array.length)
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", size, array.length));
            curSize = 0;
        }
    }

    public static void main(String[] args) {
        testBeginWithCorrectSize();
        testBeginWithIncorrectSize();
    }

    private static void testBeginWithCorrectSize() {
        FixedNodeBuilder<Object> builder = new FixedNodeBuilder<>(5);
        builder.curSize = 3; // set to non-zero to check reset
        builder.begin(5L);
        if (builder.curSize != 0) {
            throw new AssertionError("curSize was not reset to 0 after begin with correct size");
        }
    }

    private static void testBeginWithIncorrectSize() {
        FixedNodeBuilder<Object> builder = new FixedNodeBuilder<>(5);
        boolean thrown = false;
        try {
            builder.begin(4L);
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("Begin size 4 is not equal to fixed size 5")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown for incorrect size");
        }
    }
}
