/* @test    @summary Test HashMap forEachRemaining method for correct iteration and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingWithElements() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = sb::append;
        map.forEachRemaining(consumer);
        String result = sb.toString();
        boolean containsOne = result.contains("one");
        boolean containsTwo = result.contains("two");
        boolean containsThree = result.contains("three");
        if (!(containsOne && containsTwo && containsThree)) {
            throw new AssertionError("Expected all values to be processed, but got: " + result);
        }
    }

    static void testForEachRemainingWithNullAction() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Consumer<String> consumer = s -> {
            if (s.equals("one")) {
                map.put(3, "three");
            }
        };
        boolean exceptionThrown = false;
        try {
            map.forEachRemaining(consumer);
        } catch (java.util.ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        }
    }
}
