/*
 * @test
 * @summary Verifies that the replace(K, V, V) method of the empty map returned by Collections.emptyMap() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, String> empty = Collections.emptyMap();

        expectUnsupported(empty, "k", null, "v");
        expectUnsupported(empty, "k", "a", "b");
        expectUnsupported(empty, null, "a", "b");

        if (!empty.isEmpty()) {
            throw new RuntimeException("emptyMap changed after rejected replace");
        }
    }

    private static void expectUnsupported(Map<String, String> map, String key,
                                          String oldValue, String newValue) {
        try {
            map.replace(key, oldValue, newValue);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown by replace(K, V, V)");
    }
}
