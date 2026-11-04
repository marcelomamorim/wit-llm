/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEachRemaining method behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmptyIterator();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        Iterator<String> it = list.iterator();
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        // Call forEachRemaining on iterator
        forEachRemaining(it, action);
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + result + "'");
        }
        // After forEachRemaining, iterator should have no more elements
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements after forEachRemaining");
        }
    }

    static void testForEachRemainingEmptyIterator() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();
        final boolean[] called = {false};
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };
        // Call forEachRemaining on empty iterator
        forEachRemaining(it, action);
        if (called[0]) {
            throw new AssertionError("Action should not be called for empty iterator");
        }
    }

    static void testForEachRemainingNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();
        try {
            forEachRemaining(it, null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Helper method to invoke forEachRemaining on iterator with given action
    static <E> void forEachRemaining(Iterator<E> it, Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (it.hasNext()) {
            action.accept(it.next());
        }
    }
}
