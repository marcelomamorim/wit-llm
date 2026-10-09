/*
 * @test
 * @summary Test for ConcurrentSkipListMap forEach method behavior
 * @run main ConcurrentSkipListMapWitupTest_P00fc9f51a6db
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_P00fc9f51a6db {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachWithValidAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> consumer = (k, v) -> {
            result.append(k).append(":").append(v).append(";");
        };
        map.forEach(consumer);
        String expected1 = "1:one;2:two;";
        String expected2 = "2:two;1:one;";
        String actual = result.toString();
        if (!(actual.equals(expected1) || actual.equals(expected2))) {
            throw new AssertionError("Unexpected iteration order or output: " + actual);
        }
    }
}
