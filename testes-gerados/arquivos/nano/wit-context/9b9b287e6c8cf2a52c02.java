/*
 * @test
 * @summary Test for ConcurrentSkipListMap's forEachRemaining method, focusing on null action handling.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Consumer<Integer> nullConsumer = null;
        boolean exceptionThrown = false;
        try {
            map.keySet().spliterator().forEachRemaining(nullConsumer);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEachRemaining");
        }
    }

    private static void testForEachRemainingNormal() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = k -> sb.append(k);
        map.keySet().spliterator().forEachRemaining(consumer);
        String result = sb.toString();
        if (!(result.contains("1") && result.contains("2"))) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result);
        }
    }
}
