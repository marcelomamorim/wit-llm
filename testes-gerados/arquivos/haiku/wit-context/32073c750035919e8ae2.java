/*
 * @test
 * @summary Witup-style checks for Vector spliterator forEachRemaining behavior
 * @run main VectorWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testForEachRemainingVisitsAllElementsInOrder();
        testSpliteratorExhaustedAfterForEachRemaining();
        testConcurrentModificationFromActionIsDetected();
    }

    static void testNullActionThrowsNpe() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = v.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testForEachRemainingVisitsAllElementsInOrder() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = v.spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        if (!seen.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("unexpected elements: " + seen);
        }
    }

    static void testSpliteratorExhaustedAfterForEachRemaining() {
        Vector<String> v = new Vector<>(Arrays.asList("x", "y"));
        Spliterator<String> sp = v.spliterator();
        sp.forEachRemaining(s -> { });
        boolean advanced = sp.tryAdvance(s -> { });
        if (advanced) {
            throw new RuntimeException("spliterator should be exhausted");
        }
    }

    static void testConcurrentModificationFromActionIsDetected() {
        final Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = v.spliterator();
        final boolean[] first = { true };
        boolean thrown = false;
        try {
            sp.forEachRemaining(s -> {
                if (first[0]) {
                    first[0] = false;
                    v.add("d");
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException");
        }
    }
}
