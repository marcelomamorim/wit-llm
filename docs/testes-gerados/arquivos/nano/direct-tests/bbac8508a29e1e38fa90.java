/*
 * @test    @summary Test ConcurrentHashMap constructor with initialCapacity parameter
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithInitialCapacityZero();
        testConstructorWithPositiveInitialCapacity();
        testConstructorWithLargeInitialCapacity();
    }

    private static void testConstructorWithInitialCapacityZero() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(0);
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0 for initial capacity 0, but got " + map.size());
        }
    }

    private static void testConstructorWithPositiveInitialCapacity() {
        int initialCapacity = 10;
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(initialCapacity);
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0 for initial capacity 10, but got " + map.size());
        }
    }

    private static void testConstructorWithLargeInitialCapacity() {
        int largeCapacity = 10000;
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(largeCapacity);
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0 for large initial capacity, but got " + map.size());
        }
    }
}
