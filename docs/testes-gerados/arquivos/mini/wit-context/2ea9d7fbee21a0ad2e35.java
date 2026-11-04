/*
 * @test
 * @summary Test ConcurrentHashMap.forEach(Consumer) for null argument and normal iteration
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachIteratesKeys();
    }

    static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean npeThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachIteratesKeys() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        // Check that all keys are present in the output string
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new AssertionError("forEach did not iterate all keys: " + result);
        }
    }
}
