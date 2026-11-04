/*
 * @test
 * @summary Test ConcurrentHashMap.keySet(V) method for correct behavior and null handling
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testKeySetWithValidValue();
        testKeySetWithNullValue();
    }

    static void testKeySetWithValidValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        String value = "mapped";
        ConcurrentHashMap.KeySetView<String, Integer> keySetView = map.keySet(value);
        if (keySetView == null) {
            throw new AssertionError("keySet(V) returned null for valid value");
        }
        // Verify that the returned object is an instance of KeySetView
        if (!(keySetView instanceof ConcurrentHashMap.KeySetView)) {
            throw new AssertionError("Returned object is not an instance of KeySetView");
        }
        // Verify that the map inside KeySetView is the same as the original map
        if (keySetView.getMap() != map) {
            throw new AssertionError("KeySetView's map does not match the original map");
        }
        // Verify that the mappedValue is the same as the one passed
        if (keySetView.getMappedValue() != value) {
            throw new AssertionError("Mapped value inside KeySetView does not match the provided value");
        }
    }

    static void testKeySetWithNullValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        try {
            map.keySet(null);
            throw new AssertionError("Expected NullPointerException not thrown for null mappedValue");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
