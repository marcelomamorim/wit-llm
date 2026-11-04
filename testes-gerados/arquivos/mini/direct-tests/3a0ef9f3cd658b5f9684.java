/*
 * @test
 * @summary Test TreeMap.forEach method behavior including normal iteration and concurrent modification detection
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiConsumer;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testForEachNormalIteration();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachNormalIteration() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        // The keys are sorted lexicographically: one, three, two
        if (!result.equals("one=1,three=3,two=2,")) {
            throw new AssertionError("Unexpected iteration order or content: " + result);
        }
    }

    private static void testForEachNullAction() {
        TreeMap<String, Integer> map = new TreeMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);

        final int[] count = {0};
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                count[0]++;
                if (count[0] == 1) {
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("c", 3);
                }
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
