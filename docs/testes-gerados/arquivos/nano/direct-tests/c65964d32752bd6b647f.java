/* @test    @summary Test HashMap constructor with initialCapacity and loadFactor    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testHashMapConstructorValid();
        testHashMapConstructorZeroCapacity();
        testHashMapConstructorNegativeCapacity();
        testHashMapConstructorNaNLoadFactor();
        testHashMapConstructorExceedingMaxCapacity();
    }

    static void testHashMapConstructorValid() {
        HashMap<Integer, String> map = new HashMap<>(16, 0.75f);
        assert map != null : "HashMap should be instantiated";
    }

    static void testHashMapConstructorZeroCapacity() {
        try {
            HashMap<Integer, String> map = new HashMap<>(0, 0.75f);
            assert map != null : "HashMap with zero capacity should be instantiated";
        } catch (IllegalArgumentException e) {
            assert false : "Constructor with zero capacity should not throw";
        }
    }

    static void testHashMapConstructorNegativeCapacity() {
        try {
            HashMap<Integer, String> map = new HashMap<>(-1, 0.75f);
            assert false : "Constructor with negative capacity should throw";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal initial capacity") : "Expected Illegal initial capacity exception";
        }
    }

    static void testHashMapConstructorNaNLoadFactor() {
        try {
            HashMap<Integer, String> map = new HashMap<>(16, Float.NaN);
            assert false : "Constructor with NaN load factor should throw";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal load factor") : "Expected Illegal load factor exception";
        }
    }

    static void testHashMapConstructorExceedingMaxCapacity() {
        int exceedingCapacity = Integer.MAX_VALUE;
        HashMap<Integer, String> map = new HashMap<>(exceedingCapacity, 0.75f);
        assert map != null : "HashMap with exceeding capacity should be instantiated";
    }
}
