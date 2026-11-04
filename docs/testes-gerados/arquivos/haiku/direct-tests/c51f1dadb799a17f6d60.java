/*
 * @test
 * @summary Verify that the three-argument replace method of the unmodifiable map view returned by Collections.unmodifiableMap throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceMatchingValueThrows();
        testReplaceNonMatchingValueThrows();
        testReplaceWithNullValuesThrows();
        testBackingMapUnchangedAfterReplace();
    }

    static void testReplaceMatchingValueThrows() {
        Map<String, String> backing = new HashMap<>();
        backing.put("a", "1");
        Map<String, String> um = Collections.unmodifiableMap(backing);
        boolean thrown = false;
        try {
            um.replace("a", "1", "2");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for matching replace");
        }
    }

    static void testReplaceNonMatchingValueThrows() {
        Map<String, String> backing = new HashMap<>();
        backing.put("a", "1");
        Map<String, String> um = Collections.unmodifiableMap(backing);
        boolean thrown = false;
        try {
            um.replace("a", "other", "2");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for non-matching replace");
        }
    }

    static void testReplaceWithNullValuesThrows() {
        Map<String, String> backing = new HashMap<>();
        Map<String, String> um = Collections.unmodifiableMap(backing);
        boolean thrown = false;
        try {
            um.replace("missing", null, null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for null replace arguments");
        }
    }

    static void testBackingMapUnchangedAfterReplace() {
        Map<String, String> backing = new HashMap<>();
        backing.put("a", "1");
        Map<String, String> um = Collections.unmodifiableMap(backing);
        try {
            um.replace("a", "1", "2");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (!"1".equals(backing.get("a"))) {
            throw new RuntimeException("Backing map was modified: " + backing.get("a"));
        }
        if (!"1".equals(um.get("a"))) {
            throw new RuntimeException("Unmodifiable view changed: " + um.get("a"));
        }
    }
}
