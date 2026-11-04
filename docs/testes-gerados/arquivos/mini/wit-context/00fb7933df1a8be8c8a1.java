/*
 * @test
 * @summary Test ConcurrentHashMap(int initialCapacity, float loadFactor) constructor for argument validation
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testValidArguments();
        testInvalidInitialCapacity();
        testInvalidLoadFactorZero();
        testInvalidLoadFactorNegative();
    }

    private static void testValidArguments() {
        // Should construct without exception
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>(16, 0.75f);
        if (map == null) throw new AssertionError("Constructor returned null");
    }

    private static void testInvalidInitialCapacity() {
        // initialCapacity < 0 should cause IllegalArgumentException
        try {
            new ConcurrentHashMap<>(-1, 0.75f);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testInvalidLoadFactorZero() {
        // loadFactor <= 0 should cause IllegalArgumentException
        try {
            new ConcurrentHashMap<>(16, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testInvalidLoadFactorNegative() {
        // loadFactor <= 0 should cause IllegalArgumentException
        try {
            new ConcurrentHashMap<>(16, -0.1f);
            throw new AssertionError("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
