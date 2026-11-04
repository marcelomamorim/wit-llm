/*
 * @test
 * @summary Test java.util.Vector.forEach method for normal and concurrent modification behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachNormal() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        v.forEach(action);

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + result + "'");
        }
    }

    private static void testForEachNullAction() {
        Vector<String> v = new Vector<>();
        try {
            v.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");

        Consumer<String> action = new Consumer<String>() {
            private int count = 0;
            public void accept(String s) {
                count++;
                if (count == 1) {
                    v.add("z"); // structural modification during iteration
                }
            }
        };

        try {
            v.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
