/*
 * @test
 * @summary Test java.util.ArrayList.forEachRemaining(Consumer) method
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormalIteration();
        testForEachRemainingConcurrentModification();
    }

    private static void testForEachRemainingNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        java.util.Iterator<String> it = list.iterator();
        try {
            // Passing null should throw NullPointerException as per Objects.requireNonNull
            ((ArrayList.Itr) it).forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormalIteration() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        final StringBuilder sb = new StringBuilder();
        java.util.Iterator<String> it = list.iterator();
        // Cast to ArrayList.Itr to access forEachRemaining
        ArrayList.Itr itr = (ArrayList.Itr) it;

        itr.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new RuntimeException("Unexpected iteration result: " + result);
        }

        // After iteration, cursor should be at end, so forEachRemaining should do nothing
        final StringBuilder sb2 = new StringBuilder();
        itr.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb2.append(s);
            }
        });
        if (sb2.length() != 0) {
            throw new RuntimeException("forEachRemaining should not call accept after full iteration");
        }
    }

    private static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        java.util.Iterator<String> it = list.iterator();
        ArrayList.Itr itr = (ArrayList.Itr) it;

        // Modify list structurally to cause ConcurrentModificationException
        list.add("z");

        try {
            itr.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
