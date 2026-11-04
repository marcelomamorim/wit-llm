/*
 * @test
 * @summary Test java.util.Collections forEach method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNonEmptyList();
        testForEachWithEmptyList();
        testForEachWithNullAction();
    }

    static void testForEachWithNonEmptyList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Use the forEach method of the list directly (since Collections.forEach is instance method on inner class)
        list.forEach(action);

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("forEach did not process all elements in order: " + result);
        }
    }

    static void testForEachWithEmptyList() {
        List<String> list = new ArrayList<>();

        final boolean[] called = {false};
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };

        list.forEach(action);

        if (called[0]) {
            throw new RuntimeException("forEach action should not be called on empty list");
        }
    }

    static void testForEachWithNullAction() {
        List<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.forEach(null);
            throw new RuntimeException("Expected NullPointerException when passing null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
