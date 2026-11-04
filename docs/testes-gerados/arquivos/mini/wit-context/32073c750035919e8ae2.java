/*
 * @test
 * @summary Test Vector.forEachRemaining(Consumer) for null action and concurrent modification
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.spliterator().forEachRemaining(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("a");
        Consumer<String> consumer = new Consumer<String>() {
            int count = 0;
            public void accept(String s) {
                count++;
                if (count == 1) {
                    v.add("b"); // structural modification during iteration
                }
            }
        };
        try {
            v.spliterator().forEachRemaining(consumer);
            throw new Error("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        v.spliterator().forEachRemaining(consumer);
        String result = sb.toString();
        if (!"ab".equals(result) && !"ba".equals(result)) {
            throw new Error("Unexpected iteration result: " + result);
        }
    }
}
