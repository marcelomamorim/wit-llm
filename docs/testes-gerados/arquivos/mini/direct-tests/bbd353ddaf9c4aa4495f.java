/*
 * @test
 * @summary Test java.util.stream.Nodes.asPrimitiveArray() method behavior
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    // Minimal concrete subclass to test asPrimitiveArray
    static class TestNode extends Nodes.Node<int[]> {
        private final int[] data;

        TestNode(int[] data) {
            super(data.length);
            this.data = data;
        }

        @Override
        public int count() {
            return data.length;
        }

        @Override
        public int[] newArray(int size) {
            return new int[size];
        }

        @Override
        public void copyInto(int[] array, int offset) {
            System.arraycopy(data, 0, array, offset, data.length);
        }

        @Override
        public int[] asPrimitiveArray() {
            return super.asPrimitiveArray();
        }
    }

    public static void main(String[] args) {
        testEmptyNode();
        testSmallNode();
        testMaxArraySizeException();
    }

    private static void testEmptyNode() {
        TestNode node = new TestNode(new int[0]);
        int[] arr = node.asPrimitiveArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array, got length " + arr.length);
        }
    }

    private static void testSmallNode() {
        int[] input = {1, 2, 3, 4, 5};
        TestNode node = new TestNode(input);
        int[] arr = node.asPrimitiveArray();
        if (arr.length != input.length) {
            throw new AssertionError("Array length mismatch");
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != input[i]) {
                throw new AssertionError("Array content mismatch at index " + i);
            }
        }
    }

    private static void testMaxArraySizeException() {
        TestNode node = new TestNode(new int[0]) {
            @Override
            public long count() {
                return Nodes.MAX_ARRAY_SIZE;
            }

            @Override
            public int[] newArray(int size) {
                throw new AssertionError("Should not be called when size exceeds max");
            }

            @Override
            public void copyInto(int[] array, int offset) {
                throw new AssertionError("Should not be called when size exceeds max");
            }
        };

        try {
            node.asPrimitiveArray();
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(Nodes.BAD_SIZE)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
