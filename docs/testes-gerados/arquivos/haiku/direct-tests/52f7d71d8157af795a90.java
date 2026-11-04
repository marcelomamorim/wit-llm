/*
 * @test
 * @summary Verifies that putAll on an unmodifiable map view from Collections throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPutAllThrowsUnsupportedOperation();
        testPutAllEmptySourceThrowsUnsupportedOperation();
        testPutAllDoesNotModifyBackingMap();
    }

    static void testPutAllThrowsUnsupportedOperation() {
        Map<String, Integer> backing = new HashMap<>();
        Map<String, Integer> view = Collections.unmodifiableMap(backing);
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);
        boolean thrown = false;
        try {
            view.putAll(src);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from putAll");
        }
    }

    static void testPutAllEmptySourceThrowsUnsupportedOperation() {
        Map<String, Integer> view = Collections.unmodifiableMap(new HashMap<String, Integer>());
        Map<String, Integer> src = new HashMap<>();
        boolean thrown = false;
        try {
            view.putAll(src);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for empty source map");
        }
    }

    static void testPutAllDoesNotModifyBackingMap() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("x", 10);
        Map<String, Integer> view = Collections.unmodifiableMap(backing);
        Map<String, Integer> src = new HashMap<>();
        src.put("y", 20);
        try {
            view.putAll(src);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (backing.size() != 1 || !backing.containsKey("x") || backing.containsKey("y")) {
            throw new RuntimeException("Backing map was modified by putAll: " + backing);
        }
    }
}
