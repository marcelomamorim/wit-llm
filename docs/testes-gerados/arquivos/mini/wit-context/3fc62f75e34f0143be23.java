/*
 * @test
 * @summary Test java.util.HashMap.forEach(Consumer<? super K> action) for expected behavior and exceptions
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachEmptyMap();
        testForEachNonEmptyMap();
        testForEachConcurrentModification();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (sb.length() != 0) {
            throw new AssertionError("forEach on empty map should not invoke action");
        }
    }

    private static void testForEachNonEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        if (!(result.contains("a") && result.contains("b") && result.contains("c"))) {
            throw new AssertionError("forEach did not visit all keys: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("x", "1");
        map.put("y", "2");
        final StringBuilder sb = new StringBuilder();
        try {
            map.forEach(new Consumer<String>() {
                public void accept(String s) {
                    sb.append(s);
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("z", "3");
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
