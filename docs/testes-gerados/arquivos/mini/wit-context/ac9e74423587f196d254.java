/*
 * @test
 * @summary Test java.util.Vector.forEach(Consumer) for null action and concurrent modification
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalExecution();
    }

    private static void testForEachNullAction() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("a");
        final Consumer<String> action = new Consumer<String>() {
            private int count = 0;
            public void accept(String s) {
                count++;
                if (count == 1) {
                    // Modify vector structurally during iteration to trigger ConcurrentModificationException
                    v.add("b");
                }
            }
        };
        try {
            v.forEach(action);
            throw new Error("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testForEachNormalExecution() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        v.forEach(action);
        String result = sb.toString();
        if (!"xy".equals(result)) {
            throw new Error("Expected concatenated string 'xy' but got '" + result + "'");
        }
    }
}
