/*
 * @test
 * @summary Test java.util.stream.Nodes.forEach(Consumer) for null consumer and normal usage
 * @run main NodesWitupTest
 */

import java.util.function.Consumer;
import java.util.stream.Nodes;

public class NodesWitupTest {

    static class DummyNode<T> extends Nodes.Node<T> {
        final Iterable<T> c;
        DummyNode(Iterable<T> c) { this.c = c; }
        @Override
        public void forEach(Consumer<? super T> consumer) {
            c.forEach(consumer);
        }
        @Override
        public long count() { return 0; }
        @Override
        public T[] asArray(IntFunction<T[]> generator) { return null; }
        @Override
        public Nodes.Node<T> getChild(int i) { return null; }
        @Override
        public int getChildCount() { return 0; }
        @Override
        public void copyInto(T[] array, int offset) {}
    }

    public static void main(String[] args) {
        // Test 1: forEach with null consumer should throw NullPointerException
        DummyNode<String> node = new DummyNode<>(java.util.Arrays.asList("a", "b"));
        boolean npeThrown = false;
        try {
            node.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when consumer is null");
        }

        // Test 2: forEach with valid consumer should consume all elements
        final StringBuilder sb = new StringBuilder();
        node.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!"ab".equals(sb.toString())) {
            throw new AssertionError("forEach did not consume elements correctly: " + sb.toString());
        }
    }
}
