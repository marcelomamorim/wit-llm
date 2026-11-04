/*
 * @test
 * @summary Test LinkedBlockingDeque.LBDSpliterator.forEachRemaining behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {

    static class DummySpliterator<E> extends LinkedBlockingDeque.LBDSpliterator<E> {
        boolean exhausted = false;
        E lastConsumed = null;

        DummySpliterator(LinkedBlockingDeque<E> deque) {
            super(deque);
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            if (!exhausted) {
                exhausted = true;
                Node<E> p = current;
                current = null;
                forEachFrom(action, p);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");

        DummySpliterator<String> spliterator = new DummySpliterator<>(deque);

        // Test 1: NullPointerException when action is null
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }

        // Test 2: Normal consumption of elements
        final StringBuilder sb = new StringBuilder();
        spliterator.exhausted = false; // reset exhausted to false
        spliterator.current = spliterator.first;
        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two")) {
            throw new Error("forEachRemaining did not consume all elements: " + result);
        }

        // Test 3: UnsupportedOperationException if called again after exhausted
        try {
            spliterator.forEachRemaining(s -> {});
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
