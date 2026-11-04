/*
 * @test
 * @summary Test CoderResult.unmappableForLength behavior
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {
    public static void main(String[] args) {
        testValidLengths();
        testInvalidLength();
        testCacheBehavior();
    }

    private static void testValidLengths() {
        // Test lengths 1 to 4 return expected cached instances
        for (int i = 1; i <= 4; i++) {
            CoderResult cr = CoderResult.unmappableForLength(i);
            if (cr == null) {
                throw new AssertionError("Returned null for length " + i);
            }
            if (!cr.isUnmappable()) {
                throw new AssertionError("Result is not unmappable for length " + i);
            }
            if (cr.length() != i) {
                throw new AssertionError("Length mismatch: expected " + i + " but got " + cr.length());
            }
        }
    }

    private static void testInvalidLength() {
        // Test that zero and negative lengths throw IllegalArgumentException
        int[] invalidLengths = {0, -1, -100};
        for (int len : invalidLengths) {
            try {
                CoderResult.unmappableForLength(len);
                throw new AssertionError("Expected IllegalArgumentException for length " + len);
            } catch (IllegalArgumentException e) {
                // Expected
            }
        }
    }

    private static void testCacheBehavior() {
        // Test that for length > 4, the returned instance is cached and consistent
        int testLength = 10;
        CoderResult first = CoderResult.unmappableForLength(testLength);
        CoderResult second = CoderResult.unmappableForLength(testLength);
        if (first == null || second == null) {
            throw new AssertionError("Returned null for length " + testLength);
        }
        if (!first.isUnmappable() || !second.isUnmappable()) {
            throw new AssertionError("Result is not unmappable for length " + testLength);
        }
        if (first.length() != testLength || second.length() != testLength) {
            throw new AssertionError("Length mismatch for length " + testLength);
        }
        if (first != second) {
            throw new AssertionError("Expected cached instance for length " + testLength);
        }
    }
}
