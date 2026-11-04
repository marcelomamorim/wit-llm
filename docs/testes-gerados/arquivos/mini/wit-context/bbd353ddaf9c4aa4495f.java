/*
 * @test
 * @summary Test java.util.stream.Nodes.asPrimitiveArray() method for normal and boundary conditions
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    // Minimal stub class to test asPrimitiveArray() method
    // We create a minimal subclass of Nodes.ConcNode.OfPrimitive to test asPrimitiveArray
    static class TestConcNode extends Nodes.ConcNode.OfPrimitive<int[]> {

        private final int size;

        TestConcNode(int size) {
            super(null, 0, 0); // call super with dummy args as no public constructor info
            this.size = size;
        }

        @Override
        public long count() {
            return size;
        }

        @Override
        protected int[] newArray(int size) {
            return new int[size];
        }

        @Override
        public void copyInto(int[] array, int offset) {
            for (int i = 0; i < size; i++) {
                array[offset + i] = i;
            }
        }
    }

    public static void main(String[] args) {
        testNormalArrayCreation();
        testExceptionOnTooLargeSize();
    }

    private static void testNormalArrayCreation() {
        TestConcNode node = new TestConcNode(5);
        int[] arr = node.asPrimitiveArray();
        if (arr.length != 5) {
            throw new AssertionError("Expected array length 5 but got " + arr.length);
        }
        for (int i = 0; i < 5; i++) {
            if (arr[i] != i) {
                throw new AssertionError("Expected element " + i + " but got " + arr[i]);
            }
        }
    }

    private static void testExceptionOnTooLargeSize() {
        // Use size >= MAX_ARRAY_SIZE to trigger IllegalArgumentException
        final long MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
        TestConcNode node = new TestConcNode((int) MAX_ARRAY_SIZE);
        try {
            node.asPrimitiveArray();
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Stream size exceeds max array size")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
