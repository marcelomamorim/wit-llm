/*
 * @test
 * @summary Verifies that the unmodifiable map view returned by Collections.unmodifiableMap rejects replace(K, V) with UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceExistingKeyThrows() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(backing);
        boolean thrown = false;
        try {
            unmodifiable.replace("a", 2);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "replace on existing key did not throw UnsupportedOperationException");
        check(backing.get("a") == 1, "backing map was modified by replace");
    }

    static void testReplaceAbsentKeyThrows() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(backing);
        boolean thrown = false;
        try {
            unmodifiable.replace("missing", 5);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "replace on absent key did not throw UnsupportedOperationException");
        check(!backing.containsKey("missing"), "replace inserted a mapping into the backing map");
        check(backing.isEmpty(), "backing map is not empty after replace");
    }

    public static void main(String[] args) {
        testReplaceExistingKeyThrows();
        testReplaceAbsentKeyThrows();
    }
}
