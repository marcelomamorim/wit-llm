/*
 * @test
 * @summary Test for LinkedHashMap.forEach with null BiConsumer
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.forEach(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> consumer = (k, v) -> sb.append(k).append(v);
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("a1") || !result.contains("b2")) {
            System.err.println("forEach did not process all entries correctly");
            System.exit(1);
        }
    }
}
