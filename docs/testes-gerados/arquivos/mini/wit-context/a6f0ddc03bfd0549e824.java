/*
 * @test
 * @summary Test ConcurrentHashMap.keySet(V) method behavior including NPE on null argument
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testKeySetWithNonNullValue();
        testKeySetWithNullValueThrowsNPE();
        System.out.println("All tests passed.");
    }

    private static void testKeySetWithNonNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String mappedValue = "mapped";
        KeySetView<String, String> keySetView = map.keySet(mappedValue);
        if (keySetView == null) {
            throw new AssertionError("keySet returned null for non-null mappedValue");
        }
        // The returned KeySetView should hold the mappedValue as its default mapped value
        if (!mappedValue.equals(keySetView.getMappedValue())) {
            throw new AssertionError("KeySetView does not hold the expected mappedValue");
        }
    }

    private static void testKeySetWithNullValueThrowsNPE() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.keySet(null);
            throw new AssertionError("Expected NullPointerException not thrown when mappedValue is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
