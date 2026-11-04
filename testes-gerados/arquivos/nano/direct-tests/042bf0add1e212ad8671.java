/* @test    @summary Test Collections.forEach with a simple BiConsumer    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        final StringBuilder result = new StringBuilder();
        Collections.forEach(map, (k, v) -> {
            result.append(k).append(v);
        });
        String output = result.toString();
        if (!output.contains("a1") || !output.contains("b2") || !output.contains("c3")) {
            throw new AssertionError("Expected substrings not found in output: " + output);
        }
    }
}
