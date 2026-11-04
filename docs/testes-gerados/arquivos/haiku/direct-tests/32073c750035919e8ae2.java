/*
 * @test
 * @summary Tests Vector spliterator forEachRemaining: traversal, null action, and fail-fast behavior
 * @run main VectorWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.Vector;

public class VectorWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) {
        testEmptyVector();
        testFullTraversal();
        testPartialTraversalAfterTryAdvance();
        testSecondCallYieldsNothing();
        testNullActionThrowsNpe();
        testConcurrentModificationDetected();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testEmptyVector() {
        Vector<String> v = new Vector<>();
        Spliterator<String> sp = v.spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty vector should visit no elements");
    }

    static void testFullTraversal() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = v.spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "full traversal expected [a, b, c] but was " + seen);
    }

    static void testPartialTraversalAfterTryAdvance() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = v.spliterator();
        List<String> first = new ArrayList<>();
        boolean advanced = sp.tryAdvance(first::add);
        check(advanced && first.equals(Arrays.asList("a")),
              "tryAdvance should consume only 'a' but was " + first);
        List<String> rest = new ArrayList<>();
        sp.forEachRemaining(rest::add);
        check(rest.equals(Arrays.asList("b", "c")),
              "forEachRemaining should visit remaining [b, c] but was " + rest);
    }

    static void testSecondCallYieldsNothing() {
        Vector<String> v = new Vector<>(Arrays.asList("x", "y"));
        Spliterator<String> sp = v.spliterator();
        sp.forEachRemaining(s -> { });
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        check(seen.isEmpty(), "after exhaustion forEachRemaining should visit nothing but was " + seen);
    }

    static void testNullActionThrowsNpe() {
        Vector<String> v = new Vector<>(Arrays.asList("a"));
        Spliterator<String> sp = v.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should cause NullPointerException");
    }

    static void testConcurrentModificationDetected() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        Spliterator<String> sp = v.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(s -> v.add("z"));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEachRemaining should throw ConcurrentModificationException");
    }
}
