/* @test    @summary Test for CoderResult.malformedForLength(int) method    @run main CoderResultWitupTest */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testMalformedForLengthPositive();
        testMalformedForLengthZero();
        testMalformedForLengthNegative();
        testMalformedForLengthLarge();
    }

    static void testMalformedForLengthPositive() {
        int length = 3;
        CoderResult result = CoderResult.malformedForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for positive length");
        }
        if (!result.isError()) {
            throw new AssertionError("Result should be error for malformedForLength")
        }
        if (result.length() != length) {
            throw new AssertionError("Expected length " + length + " but got " + result.length());
        }
    }

    static void testMalformedForLengthLarge() {
        int length = 100;
        CoderResult result = CoderResult.malformedForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for large length");
        }
        if (!result.isError()) {
            throw new AssertionError("Result should be error for malformedForLength")
        }
        if (result.length() != length) {
            throw new AssertionError("Expected length " + length + " but got " + result.length());
        }
    }

    static void testMalformedForLengthZero() {
        try {
            CoderResult.malformedForLength(0);
            throw new AssertionError("Expected IllegalArgumentException for zero length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testMalformedForLengthNegative() {
        try {
            CoderResult.malformedForLength(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
