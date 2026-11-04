/*
 * @test
 * @summary Test LinkedHashMap.forEach(Consumer) for normal and exceptional behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachNormal() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new AssertionError("Expected iteration order 'one,two,three,' but got: " + result);
        }
    }

    static void testForEachNullAction() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        final Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                if (s.equals("one")) {
                    map.put(3, "three"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
