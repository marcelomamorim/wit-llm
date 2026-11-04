/* @test    @summary Test for CoderResult.unmappableForLength(int) method    @run main CoderResultWitupTest */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testUnmappableForLengthPositive();
        testUnmappableForLengthZero();
        testUnmappableForLengthNegative();
        testUnmappableForLengthLarge();
    }

    static void testUnmappableForLengthPositive() {
        int length = 3;
        CoderResult result = CoderResult.unmappableForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for positive length")
        }
        // Since the method returns a CoderResult instance, check its properties indirectly
        if (!result.isUnmappable() || result.length() != length) {
            throw new AssertionError("Unexpected result for length " + length);
        }
    }

    static void testUnmappableForLengthZero() {
        try {
            CoderResult.unmappableForLength(0);
            throw new AssertionError("Expected IllegalArgumentException for zero length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testUnmappableForLengthNegative() {
        try {
            CoderResult.unmappableForLength(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testUnmappableForLengthLarge() {
        int length = 10;
        CoderResult result = CoderResult.unmappableForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for large length")
        }
        if (!result.isUnmappable() || result.length() != length) {
            throw new AssertionError("Unexpected result for length " + length);
        }
    }
}
