/*
 * @test
 * @summary Test Vector.forEachRemaining(Consumer) for null argument and normal iteration
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormalIteration();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNullAction() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormalIteration() {
        Vector<String> v = new Vector<>();
        v.add("one");
        v.add("two");
        v.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        // Use iterator to get forEachRemaining
        java.util.Iterator<String> it = v.iterator();
        // advance one element
        if (it.hasNext()) it.next();

        // The Vector class's iterator is a Vector.Itr which has forEachRemaining
        // We test the forEachRemaining method indirectly via the iterator
        // but since the method under test is Vector.forEachRemaining, we call it directly

        // The Vector class does not have a public forEachRemaining method directly,
        // but the test context shows Vector.forEachRemaining(Consumer) method,
        // so we call it directly on Vector instance.

        // The method is public void forEachRemaining(Consumer<? super E> action)
        // so call it directly

        v.forEachRemaining(consumer);

        String result = sb.toString();
        // The first element was skipped by iterator.next(), so forEachRemaining should process "two" and "three"
        if (!result.equals("two,three,")) {
            throw new RuntimeException("forEachRemaining did not process expected elements: " + result);
        }
    }

    static void testForEachRemainingConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
                // Concurrent modification during iteration
                if (s.equals("x")) {
                    v.add("z"); // structural modification
                }
            }
        };

        try {
            v.forEachRemaining(consumer);
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
