/* @test    @summary Basic tests for WeakHashMap constructor and behavior    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithInvalidInitialCapacity();
        testConstructorWithValidInitialCapacity();
    }

    private static void testDefaultConstructor() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        if (map == null) {
            throw new AssertionError("Default constructor returned null");
        }
        // Basic behavior: should be empty
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after default construction");
        }
    }

    private static void testConstructorWithInvalidInitialCapacity() {
        try {
            new WeakHashMap<Object, Object>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initial capacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidInitialCapacity() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>(16);
        if (map == null) {
            throw new AssertionError("Constructor with valid initial capacity returned null");
        }
        // Should be empty initially
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after construction with initial capacity");
        }
    }
}
