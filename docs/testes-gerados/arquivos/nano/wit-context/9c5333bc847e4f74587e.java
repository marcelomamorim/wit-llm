/* @test    @summary Test for WeakHashMap constructor with initialCapacity parameter, covering exception paths.    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNegativeInitialCapacity();
        testLargeInitialCapacity();
        testValidInitialCapacity();
    }

    private static void testNegativeInitialCapacity() {
        try {
            new WeakHashMap(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testLargeInitialCapacity() {
        try {
            int largeCapacity = 1 << 30; // 1073741824
            new WeakHashMap(largeCapacity);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected IllegalArgumentException for large initialCapacity");
        }
    }

    private static void testValidInitialCapacity() {
        try {
            WeakHashMap<String, String> map = new WeakHashMap<>(10);
            if (map == null) {
                System.err.println("Failed: Map should not be null for valid initialCapacity");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid initialCapacity: " + e);
        }
    }
}
