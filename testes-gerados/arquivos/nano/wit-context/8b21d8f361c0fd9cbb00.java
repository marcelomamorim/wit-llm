/*
 * @test
 * @summary Tests HashMap.forEach for null action handling
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyMap();
        testForEachWithPopulatedMap();
    }

    private static void testForEachWithNullAction() {
        HashMap<Integer, String> map = new HashMap<>();
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when forEach called with null action");
        }
    }

    private static void testForEachWithEmptyMap() {
        HashMap<Integer, String> map = new HashMap<>();
        final boolean[] called = {false};
        map.forEach(value -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("forEach with empty map should not invoke consumer");
        }
    }

    private static void testForEachWithPopulatedMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        final StringBuilder sb = new StringBuilder();
        map.forEach(value -> sb.append(value));
        String result = sb.toString();
        if (!(result.contains("one") && result.contains("two"))) {
            throw new AssertionError("forEach did not process all values correctly");
        }
    }
}
