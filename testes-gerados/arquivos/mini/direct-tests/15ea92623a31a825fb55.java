/*
 * @test
 * @summary Test java.util.ArrayList.forEach method behavior including concurrent modification detection
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachProcessesAllElements();
        testForEachThrowsOnNullAction();
        testForEachThrowsOnConcurrentModification();
    }

    private static void testForEachProcessesAllElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        list.forEach(action);

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("forEach did not process all elements in order, got: " + result);
        }
    }

    private static void testForEachThrowsOnNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.forEach(null);
            throw new AssertionError("forEach should throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachThrowsOnConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        Consumer<String> action = new Consumer<String>() {
            private int count = 0;
            public void accept(String s) {
                count++;
                if (count == 1) {
                    list.add("z"); // structural modification during iteration
                }
            }
        };

        try {
            list.forEach(action);
            throw new AssertionError("forEach should throw ConcurrentModificationException on concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
