/*
 * @test
 * @summary Test HashSet(int initialCapacity, float loadFactor) constructor for exception and normal cases
 * @run main HashSetWitupTest
 */

import java.util.HashSet;
import java.lang.IllegalArgumentException;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testIllegalArgumentExceptionNegativeInitialCapacity();
        testIllegalArgumentExceptionZeroOrNegativeLoadFactor();
        testIllegalArgumentExceptionLargeInitialCapacityWithNonPositiveLoadFactor();
    }

    private static void testValidConstruction() {
        // Normal case: initialCapacity and loadFactor valid
        HashSet<Object> set = new HashSet<>(16, 0.75f);
        if (set == null || set.size() != 0) {
            throw new AssertionError("HashSet construction failed for valid parameters");
        }
    }

    private static void testIllegalArgumentExceptionNegativeInitialCapacity() {
        try {
            new HashSet<>(-1, 0.75f);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testIllegalArgumentExceptionZeroOrNegativeLoadFactor() {
        // loadFactor <= 0 with initialCapacity in [0, 1073741824]
        try {
            new HashSet<>(16, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new HashSet<>(16, -0.1f);
            throw new AssertionError("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testIllegalArgumentExceptionLargeInitialCapacityWithNonPositiveLoadFactor() {
        // initialCapacity > 1073741824 and loadFactor <= 0
        try {
            new HashSet<>(1073741825, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for large initialCapacity and zero loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new HashSet<>(1073741825, -1.0f);
            throw new AssertionError("Expected IllegalArgumentException for large initialCapacity and negative loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
