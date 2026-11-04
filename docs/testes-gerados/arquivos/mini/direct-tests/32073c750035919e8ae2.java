/*
 * @test
 * @summary Test Vector.forEachRemaining(Consumer) for normal and concurrent modification cases
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");

        final StringBuilder sb = new StringBuilder();

        java.util.Iterator<String> it = v.iterator();
        // Consume one element to advance iterator index
        if (it.hasNext()) {
            sb.append(it.next());
        }

        // Use forEachRemaining to consume the rest
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        // The StringBuilder should contain all elements in order
        // first element from next(), then remaining from forEachRemaining
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc' but got '" + result + "'");
        }
    }

    static void testForEachRemainingNullAction() {
        Vector<String> v = new Vector<>();
        v.add("x");
        java.util.Iterator<String> it = v.iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("1");
        v.add("2");
        v.add("3");

        java.util.Iterator<String> it = v.iterator();

        // Modify vector structurally after iterator creation
        v.add("4");

        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
