/* @test
   @summary Verifies the forEach method of ConcurrentHashMap.
   @run main ConcurrentHashMapWitupTest_Pbc3ca0f1dd8f
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pbc3ca0f1dd8f {
    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithEntries() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> {
            result.append(k).append(v);
        });
        String combined = result.toString();
        boolean condition = combined.contains("a1") && combined.contains("b2") && combined.contains("c3");
        if (!condition) {
            throw new AssertionError("forEach did not process all entries correctly: " + combined);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentHashMap<String, String> emptyMap = new ConcurrentHashMap<>();
        StringBuilder result = new StringBuilder();
        emptyMap.forEach((k, v) -> {
            result.append(k).append(v);
        });
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result for empty map, but got: " + result.toString());
        }
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null action");
        }
    }
}
