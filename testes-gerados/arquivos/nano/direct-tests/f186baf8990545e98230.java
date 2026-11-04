/* @test    @summary Test TreeMap forEachRemaining method for correct iteration and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
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
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = sb::append;
        map.values().iterator().forEachRemaining(consumer);
        String result = sb.toString();
        boolean containsOne = result.contains("one");
        boolean containsTwo = result.contains("two");
        boolean containsThree = result.contains("three");
        if (!(containsOne && containsTwo && containsThree)) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result);
        }
    }

    static void testForEachRemainingNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.values().iterator().forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<String> iterator = map.values().iterator();
        // Advance iterator to set current
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Modify map structurally
        map.put(3, "three");
        try {
            iterator.forEachRemaining(s -> {});
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException expected) {
            // expected
        }
    }
}
