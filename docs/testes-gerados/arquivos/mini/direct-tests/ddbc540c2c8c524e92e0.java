/*
 * @test
 * @summary Test TreeMap.forEachRemaining method behavior including normal iteration and exceptions
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<Integer, String>> action = new Consumer<Map.Entry<Integer, String>>() {
            public void accept(Map.Entry<Integer, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        // Use entrySet iterator and call forEachRemaining on it
        java.util.Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        it.forEachRemaining(action);

        String result = sb.toString();
        if (!result.contains("1=one") || !result.contains("2=two") || !result.contains("3=three")) {
            throw new AssertionError("forEachRemaining did not visit all entries: " + result);
        }
    }

    static void testForEachRemainingNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        java.util.Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        java.util.Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        // Modify map structurally after iterator creation to cause ConcurrentModificationException
        map.put(3, "three");

        try {
            it.forEachRemaining(new Consumer<Map.Entry<Integer, String>>() {
                public void accept(Map.Entry<Integer, String> e) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
