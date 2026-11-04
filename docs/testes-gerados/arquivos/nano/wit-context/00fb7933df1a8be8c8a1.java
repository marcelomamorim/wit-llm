/*
 * @test
 * @summary Testa o construtor de ConcurrentHashMap com initialCapacity e loadFactor.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testValidParameters();
        testInvalidLoadFactorZero();
        testInvalidLoadFactorNegative();
        testInvalidInitialCapacityNegative();
        testInvalidInitialCapacityZero();
    }

    static void testValidParameters() {
        try {
            ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(10, 0.75f);
            if (map == null) {
                throw new AssertionError("Map should not be null for valid parameters");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid parameters", e);
        }
    }

    static void testInvalidLoadFactorZero() {
        try {
            new ConcurrentHashMap<Integer, String>(10, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for loadFactor=0.0f");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testInvalidLoadFactorNegative() {
        try {
            new ConcurrentHashMap<Integer, String>(10, -0.5f);
            throw new AssertionError("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testInvalidInitialCapacityNegative() {
        try {
            new ConcurrentHashMap<Integer, String>(-5, 0.75f);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testInvalidInitialCapacityZero() {
        try {
            new ConcurrentHashMap<Integer, String>(0, 0.75f);
            // Zero initial capacity is valid, so no exception expected
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected exception for zero initialCapacity", e);
        }
    }
}
