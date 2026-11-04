/*
 * @test
 * @summary Tests for java.util.WeakHashMap's tryAdvance method.
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithValidEntry();
    }

    private static void testTryAdvanceWithNullAction() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithEmptyMap() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        final boolean[] called = {false};
        boolean result = map.tryAdvance(entry -> {
            called[0] = true;
        });
        if (result != false) {
            throw new AssertionError("Expected tryAdvance to return false on empty map");
        }
        if (called[0]) {
            throw new AssertionError("Action should not be called on empty map");
        }
    }

    private static void testTryAdvanceWithValidEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        final boolean[] called = {false};
        boolean result = map.tryAdvance(entry -> {
            if (entry.getKey().equals("key1") && entry.getValue().equals("value1")) {
                called[0] = true;
            }
        });
        if (result != true) {
            throw new AssertionError("Expected tryAdvance to return true when entry exists");
        }
        if (!called[0]) {
            throw new AssertionError("Action was not called with the expected entry");
        }
    }
}
