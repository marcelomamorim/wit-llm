/*
 * @test
 * @summary Test java.util.TreeMap.forEach method for null action and concurrent modification
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;
import java.util.Objects;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalIteration();
    }

    private static void testForEachNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        final BiConsumer<Integer, String> action = new BiConsumer<Integer, String>() {
            private boolean first = true;
            @Override
            public void accept(Integer k, String v) {
                if (first) {
                    first = false;
                    map.put(3, "three"); // structural modification during iteration
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

    private static void testForEachNormalIteration() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new BiConsumer<Integer, String>() {
            @Override
            public void accept(Integer k, String v) {
                sb.append(k).append(":").append(v).append(",");
            }
        });

        String result = sb.toString();
        if (!result.contains("10:ten") || !result.contains("20:twenty") || !result.contains("30:thirty")) {
            throw new AssertionError("forEach did not iterate all entries correctly: " + result);
        }
    }
}
