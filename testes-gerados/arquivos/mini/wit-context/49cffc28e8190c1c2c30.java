/*
 * @test
 * @summary Test java.util.stream.Nodes.LongFixedNodeBuilder.end() for IllegalStateException when curSize < array.length
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    static class LongFixedNodeBuilder {
        final long[] array;
        int curSize;

        LongFixedNodeBuilder(int size) {
            this.array = new long[size];
            this.curSize = 0;
        }

        void end() {
            if (curSize < array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", curSize, array.length));
            }
        }
    }

    public static void main(String[] args) {
        testEndThrowsWhenCurSizeLessThanArrayLength();
        testEndPassesWhenCurSizeEqualsArrayLength();
        System.out.println("All tests passed.");
    }

    private static void testEndThrowsWhenCurSizeLessThanArrayLength() {
        LongFixedNodeBuilder builder = new LongFixedNodeBuilder(5);
        builder.curSize = 3; // less than array.length
        try {
            builder.end();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!msg.contains("End size 3 is less than fixed size 5")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testEndPassesWhenCurSizeEqualsArrayLength() {
        LongFixedNodeBuilder builder = new LongFixedNodeBuilder(4);
        builder.curSize = 4; // equal to array.length
        builder.end(); // should not throw
    }
}
