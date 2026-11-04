/* @test    @summary Test HashSet constructor with initial capacity    @run main HashSetWitupTest */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testHashSetWithInitialCapacity();
        testHashSetWithZeroCapacity();
        testHashSetWithLargeCapacity();
    }

    public static void testHashSetWithInitialCapacity() {
        int capacity = 10;
        HashSet<Object> set = new HashSet<>(capacity);
        if (set == null) {
            throw new AssertionError("HashSet instance is null");
        }
        // Add an element and verify size
        set.add("element");
        if (set.size() != 1) {
            throw new AssertionError("Expected size 1, but got " + set.size());
        }
    }

    public static void testHashSetWithZeroCapacity() {
        int capacity = 0;
        HashSet<Object> set = new HashSet<>(capacity);
        if (set == null) {
            throw new AssertionError("HashSet instance is null");
        }
        set.add(123);
        if (set.size() != 1) {
            throw new AssertionError("Expected size 1, but got " + set.size());
        }
    }

    public static void testHashSetWithLargeCapacity() {
        int capacity = 1000;
        HashSet<Object> set = new HashSet<>(capacity);
        if (set == null) {
            throw new AssertionError("HashSet instance is null");
        }
        set.add(new Object());
        if (set.size() != 1) {
            throw new AssertionError("Expected size 1, but got " + set.size());
        }
    }
}
