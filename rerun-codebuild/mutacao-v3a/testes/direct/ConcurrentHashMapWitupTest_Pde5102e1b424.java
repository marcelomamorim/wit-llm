/* @test    @summary Test for ConcurrentHashMap forEach method to ensure it iterates over all entries without skipping or error.    @run main ConcurrentHashMapWitupTest_Pde5102e1b424 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pde5102e1b424 {

    public static void main(String[] args) {
        testForEachIteratesAllEntries();
        testForEachNullAction();
    }

    static void testForEachIteratesAllEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach((key, value) -> {
            result.append(key).append(':').append(value).append(';');
        });
        String resStr = result.toString();
        boolean hasA = resStr.contains("a:1;");
        boolean hasB = resStr.contains("b:2;");
        boolean hasC = resStr.contains("c:3;");
        if (!(hasA && hasB && hasC)) {
            throw new AssertionError("forEach did not visit all entries: " + resStr);
        }
    }

    static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "y");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null action");
        }
    }
}
