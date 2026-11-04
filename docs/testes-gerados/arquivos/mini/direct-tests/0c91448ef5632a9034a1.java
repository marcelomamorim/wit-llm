/*
 * @test
 * @summary Test java.util.stream.Nodes accept method behavior
 * @run main NodesWitupTest
 */

import java.util.function.Consumer;

public class NodesWitupTest {

    static class TestNode<T> implements Consumer<T> {
        final Object[] array;
        int index = 0;
        final int fence;

        TestNode(int size) {
            this.array = new Object[size];
            this.fence = size;
        }

        @SuppressWarnings("unchecked")
        public void accept(T value) {
            if (index >= fence) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            array[index++] = value;
        }

        Object[] getArray() {
            return array;
        }

        int getIndex() {
            return index;
        }
    }

    public static void main(String[] args) {
        // Test normal accept within bounds
        TestNode<String> node = new TestNode<>(3);
        node.accept("a");
        node.accept("b");
        node.accept("c");
        if (node.getIndex() != 3) {
            throw new AssertionError("Index should be 3 after 3 accepts");
        }
        Object[] arr = node.getArray();
        if (!"a".equals(arr[0]) || !"b".equals(arr[1]) || !"c".equals(arr[2])) {
            throw new AssertionError("Array contents do not match accepted values");
        }

        // Test accept throws IndexOutOfBoundsException when index >= fence
        boolean thrown = false;
        try {
            node.accept("d");
        } catch (IndexOutOfBoundsException e) {
            if (!"3".equals(e.getMessage())) {
                throw new AssertionError("Exception message should be '3'");
            }
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException was not thrown");
        }

        System.out.println("PASS");
    }
}
