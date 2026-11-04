/* @test    @summary Test TreeMap.forEach method for correct iteration and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.BiConsumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachIteration();
        testForEachConcurrentModification();
    }

    private static void testForEachIteration() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> {
            result.append(k).append(":").append(v).append(",");
        });
        String expected = "1:one,2:two,3:three,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("forEach iteration failed: expected " + expected + " but got " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            map.forEach((k, v) -> {
                if (k == 2) {
                    map.put(4, "four"); // structural modification during iteration
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // expected exception
        }
    }
}
