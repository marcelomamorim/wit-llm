/*
 * @test
 * @summary Test java.util.stream.Nodes.asArray method behavior
 * @run main NodesWitupTest
 */

import java.util.function.IntFunction;

public class NodesWitupTest {

    static class TestNode<T> {
        final T[] array;
        final int curSize;

        TestNode(T[] array, int curSize) {
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
        testAsArrayReturnsArrayWhenLengthEqualsCurSize();
        testAsArrayThrowsWhenLengthDiffersFromCurSize();
    }

    private static void testAsArrayReturnsArrayWhenLengthEqualsCurSize() {
        Integer[] arr = new Integer[] {1, 2, 3};
        TestNode<Integer> node = new TestNode<>(arr, 3);
        Integer[] result = node.asArray(size -> new Integer[size]);
        if (result != arr) {
            throw new AssertionError("Expected returned array to be the same instance");
        }
    }

    private static void testAsArrayThrowsWhenLengthDiffersFromCurSize() {
        Integer[] arr = new Integer[] {1, 2, 3};
        TestNode<Integer> node = new TestNode<>(arr, 2);
        try {
            node.asArray(size -> new Integer[size]);
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
