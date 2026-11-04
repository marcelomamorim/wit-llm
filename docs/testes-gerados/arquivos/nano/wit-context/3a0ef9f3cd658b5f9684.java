/*
 * @test
 * @summary Tests for java.util.TreeMap forEach method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.function.BiConsumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
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
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        StringBuilder sb = new StringBuilder();
        BiConsumer<Integer, String> consumer = (k, v) -> sb.append(k).append(":").append(v).append(",");
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("1:one") || !result.contains("2:two")) {
            throw new AssertionError("forEach did not process all entries correctly. Result: " + result);
        }
    }
}
