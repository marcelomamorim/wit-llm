/* @test    @summary Test for IdentityHashMap.forEach with null BiConsumer    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;
import java.util.function.BiConsumer;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
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
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> consumer = (k, v) -> sb.append(k).append(":").append(v).append(";");
        map.forEach(consumer);
        String result = sb.toString();
        if (!(result.contains("key1:value1") && result.contains("key2:value2"))) {
            throw new AssertionError("forEach did not process all entries correctly. Result: " + result);
        }
    }
}
