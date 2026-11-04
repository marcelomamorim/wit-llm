/*
 * @test    @summary Test for ConcurrentHashMap constructor with initialCapacity
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testValidInitialCapacity();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
    }

    static void testValidInitialCapacity() {
        try {
            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(10);
            if (map.size() != 0) {
                throw new AssertionError("Map should be empty after construction");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid initial capacity", e);
        }
    }

    static void testZeroInitialCapacity() {
        try {
            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(0);
            if (map.size() != 0) {
                throw new AssertionError("Map should be empty after construction with zero initial capacity");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero initial capacity", e);
        }
    }

    static void testNegativeInitialCapacity() {
        try {
            new ConcurrentHashMap<Integer, String>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initial capacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative initial capacity", e);
        }
    }
}
