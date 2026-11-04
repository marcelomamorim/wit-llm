/*
 * @test
 * @summary Verifies Collections.emptyMap().forEach rejects null actions and never invokes the action on an empty map
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> empty = Collections.emptyMap();

        int[] calls = {0};
        BiConsumer<String, Integer> action = (k, v) -> calls[0]++;
        empty.forEach(action);
        if (calls[0] != 0) {
            throw new RuntimeException("action invoked " + calls[0] + " times on empty map");
        }

        try {
            empty.forEach(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
