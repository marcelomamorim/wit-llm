/*
 * @test    @summary Test forEach method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithElements() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s).append(",");
        map.forEach(consumer);
        String result = sb.toString();
        boolean condition = result.contains("one") && result.contains("two") && result.contains("three");
        if (!condition) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s).append(",");
        map.forEach(consumer);
        if (!sb.toString().isEmpty()) {
            throw new AssertionError("forEach on empty map should not process any elements: " + sb.toString());
        }
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("forEach with null action should throw NullPointerException");
        }
    }
}
