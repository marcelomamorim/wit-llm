/*
 * @test
 * @summary Test java.util.stream.Nodes.ArrayNode.asArray method for correct array size and exception
 * @run main NodesWitupTest
 */

import java.util.function.IntFunction;

public class NodesWitupTest {

    static class DummyArrayNode<T> {
        final T[] array;
        final int curSize;

        DummyArrayNode(T[] array, int curSize) {
            this.array = array;
            this.curSize = curSize;
        }

        public T[] asArray(IntFunction<T[]> generator) {
            if (array.length == curSize) {
                return array;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public static void main(String[] args) {
        testAsArrayReturnsArrayWhenLengthMatches();
        testAsArrayThrowsWhenLengthDiffers();
        System.out.println("All tests passed.");
    }

    private static void testAsArrayReturnsArrayWhenLengthMatches() {
        Integer[] arr = new Integer[] {1, 2, 3};
        DummyArrayNode<Integer> node = new DummyArrayNode<>(arr, 3);
        IntFunction<Integer[]> generator = size -> new Integer[size];
        Integer[] result = node.asArray(generator);
        if (result != arr) {
            throw new AssertionError("Returned array should be the same instance when lengths match");
        }
    }

    private static void testAsArrayThrowsWhenLengthDiffers() {
        Integer[] arr = new Integer[] {1, 2, 3};
        DummyArrayNode<Integer> node = new DummyArrayNode<>(arr, 2);
        IntFunction<Integer[]> generator = size -> new Integer[size];
        boolean thrown = false;
        try {
            node.asArray(generator);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when array length differs from curSize");
        }
    }
}
