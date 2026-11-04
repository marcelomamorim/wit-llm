/*
 * @test
 * @summary Verifies that the unmodifiable map wrapper rejects putIfAbsent with UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAbsentKeyThrows();
        testPresentKeyThrows();
        testNullArgumentsThrow();
        testBackingMapUnchanged();
    }

    static void testAbsentKeyThrows() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(backing);
        try {
            um.putIfAbsent("b", 2);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testPresentKeyThrows() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(backing);
        try {
            um.putIfAbsent("a", 5);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testNullArgumentsThrow() {
        Map<String, Integer> um = Collections.unmodifiableMap(new HashMap<String, Integer>());
        try {
            um.putIfAbsent(null, null);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testBackingMapUnchanged() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(backing);
        try {
            um.putIfAbsent("b", 2);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (backing.size() != 1 || backing.containsKey("b")) {
            throw new AssertionError("Backing map was modified: " + backing);
        }
        if (um.get("b") != null) {
            throw new AssertionError("Unmodifiable view exposes unexpected entry");
        }
    }
}
