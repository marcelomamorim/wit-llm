/*
 * @test
 * @summary Test for CoderResult.unmappableForLength(int)
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testUnmappableForLengthPositive();
        testUnmappableForLengthZero();
        testUnmappableForLengthNegative();
        testUnmappableForLengthLarge();
    }

    private static void testUnmappableForLengthPositive() {
        try {
            CoderResult result = CoderResult.unmappableForLength(3);
            if (result == null) {
                throw new AssertionError("Result should not be null for positive length");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive length", e);
        }
    }

    private static void testUnmappableForLengthZero() {
        try {
            CoderResult.unmappableForLength(0);
            throw new AssertionError("Expected IllegalArgumentException for length 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for length 0", e);
        }
    }

    private static void testUnmappableForLengthNegative() {
        try {
            CoderResult.unmappableForLength(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative length", e);
        }
    }

    private static void testUnmappableForLengthLarge() {
        try {
            CoderResult result = CoderResult.unmappableForLength(10);
            if (result == null) {
                throw new AssertionError("Result should not be null for large length");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large length", e);
        }
    }
}
