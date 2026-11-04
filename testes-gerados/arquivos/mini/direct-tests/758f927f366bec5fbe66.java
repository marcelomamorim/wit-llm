/*
 * @test
 * @summary Test java.util.stream.Nodes forEach method behavior
 * @run main NodesWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Nodes;

public class NodesWitupTest {

    static class TestNode<T> extends Nodes {
        final List<T> c;

        TestNode(List<T> c) {
            this.c = c;
        }

        public void forEach(Consumer<? super T> consumer) {
            c.forEach(consumer);
        }
    }

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmpty();
    }

    private static void testForEachWithElements() {
        List<String> data = new ArrayList<>();
        data.add("a");
        data.add("b");
        data.add("c");

        TestNode<String> node = new TestNode<>(data);

        final List<String> collected = new ArrayList<>();
        node.forEach(collected::add);

        if (collected.size() != data.size()) {
            throw new AssertionError("forEach did not process all elements");
        }
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).equals(collected.get(i))) {
                throw new AssertionError("forEach processed elements in wrong order or wrong elements");
            }
        }
    }

    private static void testForEachWithEmpty() {
        List<String> empty = new ArrayList<>();
        TestNode<String> node = new TestNode<>(empty);

        final List<String> collected = new ArrayList<>();
        node.forEach(collected::add);

        if (!collected.isEmpty()) {
            throw new AssertionError("forEach on empty node should not add elements");
        }
    }
}
