/*
 * @test
 * @summary Test ConcurrentHashMap.keySet(V mappedValue) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testKeySetWithNonNullMappedValue();
        testKeySetWithNullMappedValue();
    }

    private static void testKeySetWithNonNullMappedValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String mappedValue = "mapped";
        KeySetView<String, String> keySetView = map.keySet(mappedValue);

        if (keySetView == null) {
            throw new AssertionError("keySet returned null");
        }

        // The mappedValue should be accessible via the KeySetView
        // We test that the default mappedValue is the one passed
        if (!mappedValue.equals(keySetView.getMappedValue())) {
            throw new AssertionError("Mapped value mismatch");
        }

        // The keySet view should be empty initially
        if (!keySetView.isEmpty()) {
            throw new AssertionError("KeySetView should be empty initially");
        }

        // Add a key via the keySet view
        boolean added = keySetView.add("key1");
        if (!added) {
            throw new AssertionError("Failed to add key via keySet view");
        }

        // The map should contain the key with the mappedValue
        if (!mappedValue.equals(map.get("key1"))) {
            throw new AssertionError("Map does not contain expected mapped value for key");
        }

        // The keySet view should contain the key
        if (!keySetView.contains("key1")) {
            throw new AssertionError("KeySetView does not contain added key");
        }
    }

    private static void testKeySetWithNullMappedValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.keySet(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
