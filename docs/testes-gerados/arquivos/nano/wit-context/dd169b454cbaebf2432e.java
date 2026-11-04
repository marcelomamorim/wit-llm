/*
 * @test
 * @summary Test for ConcurrentHashMap.forEach method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
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
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        map.forEach(consumer);
        String result = sb.toString();
        if (!(result.contains("1") && result.contains("2"))) {
            throw new AssertionError("forEach did not process all values: " + result);
        }
    }
}
