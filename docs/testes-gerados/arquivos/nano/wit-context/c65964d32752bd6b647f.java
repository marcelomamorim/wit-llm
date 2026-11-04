/* @test    @summary Test HashMap constructor with valid and invalid parameters    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNegativeInitialCapacity();
        testZeroInitialCapacity();
        testExcessiveInitialCapacity();
        testInvalidLoadFactorNaN();
        testInvalidLoadFactorZero();
    }

    static void testValidConstructor() {
        HashMap<Integer, String> map = new HashMap<>(10, 0.75f);
        assert map != null : "HashMap should be instantiated with valid parameters";
    }

    static void testNegativeInitialCapacity() {
        try {
            new HashMap<Integer, String>(-1, 0.75f);
            assert false : "Expected IllegalArgumentException for negative initialCapacity";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal initial capacity") : "Exception message should mention illegal initial capacity";
        }
    }

    static void testZeroInitialCapacity() {
        try {
            new HashMap<Integer, String>(0, 0.75f);
            // Zero initial capacity is valid, should not throw
        } catch (IllegalArgumentException e) {
            assert false : "Zero initial capacity should not throw";
        }
    }

    static void testExcessiveInitialCapacity() {
        int maxCapacity = Integer.MAX_VALUE;
        HashMap<Integer, String> map = new HashMap<>(maxCapacity, 0.75f);
        assert map != null : "HashMap with max capacity should be instantiated";
    }

    static void testInvalidLoadFactorNaN() {
        try {
            new HashMap<Integer, String>(10, Float.NaN);
            assert false : "Expected IllegalArgumentException for NaN loadFactor";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal load factor") : "Exception message should mention illegal load factor";
        }
    }

    static void testInvalidLoadFactorZero() {
        try {
            new HashMap<Integer, String>(10, 0f);
            assert false : "Expected IllegalArgumentException for zero loadFactor";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Illegal load factor") : "Exception message should mention illegal load factor";
        }
    }
}
