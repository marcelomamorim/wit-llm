/*
 * @test    @summary Test for ConcurrentSkipListMap.forEachRemaining method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyMap();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = k -> sb.append(k).append(",");
        map.forEachRemaining(consumer);
        String result = sb.toString();
        boolean condition = result.equals("1,2,3,") || result.equals("1,2,3,");
        if (!condition) {
            throw new AssertionError("Expected '1,2,3,' but got: " + result);
        }
    }

    static void testForEachRemainingWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = k -> sb.append(k).append(",");
        map.forEachRemaining(consumer);
        String result = sb.toString();
        if (!result.isEmpty()) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }

    static void testForEachRemainingNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean thrown = false;
        try {
            map.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null action");
        }
    }
}
