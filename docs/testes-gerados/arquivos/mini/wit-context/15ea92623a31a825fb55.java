/*
 * @test
 * @summary Test java.util.ArrayList.forEach method for null action and concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalExecution();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        try {
            list.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        final Consumer<String> action = new Consumer<String>() {
            private int count = 0;
            @Override
            public void accept(String s) {
                count++;
                if (count == 1) {
                    // Structural modification during iteration
                    list.add("three");
                }
            }
        };

        try {
            list.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testForEachNormalExecution() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            @Override
            public void accept(String s) {
                sb.append(s);
            }
        };

        list.forEach(action);

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + result + "'");
        }
    }
}
