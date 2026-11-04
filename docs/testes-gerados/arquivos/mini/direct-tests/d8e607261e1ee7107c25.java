/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEachRemaining method behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyList();
        testForEachRemainingWithNullAction();
    }

    static void testForEachRemainingWithElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        final List<String> collected = new ArrayList<>();

        // Create iterator and call forEachRemaining with a consumer that collects elements
        CopyOnWriteArrayList<String> copy = list;
        java.util.Iterator<String> it = copy.iterator();

        // Advance iterator once to move cursor
        if (it.hasNext()) {
            it.next();
        }

        // Use forEachRemaining on iterator
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });

        // After advancing once, forEachRemaining should process remaining elements
        if (collected.size() != 2 || !collected.get(0).equals("b") || !collected.get(1).equals("c")) {
            throw new AssertionError("forEachRemaining did not process expected elements: " + collected);
        }
    }

    static void testForEachRemainingWithEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        final List<String> collected = new ArrayList<>();

        java.util.Iterator<String> it = list.iterator();

        // forEachRemaining on empty iterator should not call accept
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });

        if (!collected.isEmpty()) {
            throw new AssertionError("forEachRemaining on empty iterator should not call accept");
        }
    }

    static void testForEachRemainingWithNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        java.util.Iterator<String> it = list.iterator();

        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
