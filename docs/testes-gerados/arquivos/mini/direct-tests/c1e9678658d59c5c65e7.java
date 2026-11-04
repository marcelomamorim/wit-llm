/*
 * @test
 * @summary Test forEachRemaining method delegation in Collections
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static class TestIterator<E> implements Iterator<E> {
        private final List<E> list;
        private int index = 0;

        TestIterator(List<E> list) {
            this.list = list;
        }

        public boolean hasNext() {
            return index < list.size();
        }

        public E next() {
            return list.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void forEachRemaining(Consumer<? super E> action) {
            while (index < list.size()) {
                action.accept(list.get(index++));
            }
        }
    }

    static class TestForEachRemaining<E> {
        private final Iterator<E> it;

        TestForEachRemaining(Iterator<E> it) {
            this.it = it;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            it.forEachRemaining(action);
        }
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("a");
        data.add("b");
        data.add("c");

        TestIterator<String> testIt = new TestIterator<>(data);
        TestForEachRemaining<String> wrapper = new TestForEachRemaining<>(testIt);

        final StringBuilder sb = new StringBuilder();
        wrapper.forEachRemaining(s -> sb.append(s));

        if (!"abc".equals(sb.toString())) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly: " + sb.toString());
        }

        // Test that after consuming all elements, forEachRemaining does nothing
        final int[] count = {0};
        wrapper.forEachRemaining(s -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("forEachRemaining should not call action after iterator exhausted");
        }

        System.out.println("Test passed.");
    }
}
