/*
 * @test
 * @summary Test ArrayList.forEachRemaining behavior including concurrent modification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        final StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();

        // Use forEachRemaining to append remaining elements
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        // The StringBuilder should contain all elements concatenated
        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("Expected 'abc' but got '" + sb.toString() + "'");
        }

        // After forEachRemaining, iterator should have no more elements
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no remaining elements after forEachRemaining");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        Iterator<String> it = list.iterator();

        // Modify list structurally after iterator creation
        list.add("z");

        boolean threw = false;
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
        } catch (ConcurrentModificationException cme) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        }
    }

    static void testForEachRemainingNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("test");

        Iterator<String> it = list.iterator();

        boolean threw = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException npe) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected NullPointerException not thrown when action is null");
        }
    }
}
