/*
 * @test
 * @summary Verifies that the map returned by Collections.singletonMap rejects replaceAll with UnsupportedOperationException and keeps its entry unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> map = Collections.singletonMap("a", 1);
        try {
            map.replaceAll((k, v) -> v + 1);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
        if (map.size() != 1) {
            throw new RuntimeException("Size changed after failed replaceAll: " + map.size());
        }
        if (!Integer.valueOf(1).equals(map.get("a"))) {
            throw new RuntimeException("Value changed after failed replaceAll: " + map.get("a"));
        }
    }
}
