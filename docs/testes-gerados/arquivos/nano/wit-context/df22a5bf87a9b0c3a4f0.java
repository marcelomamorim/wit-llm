/* @test    @summary Test for CoderResult.malformedForLength(int) with valid positive lengths    @run main CoderResultWitupTest */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testMalformedForLengthPositive();
        testMalformedForLengthZero();
        testMalformedForLengthNegative();
        testMalformedForLengthLarge();
    }

    private static void testMalformedForLengthPositive() {
        int length = 3;
        CoderResult result = CoderResult.malformedForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for positive length") ;
        }
        // Additional checks can be added if CoderResult exposes state
    }

    private static void testMalformedForLengthZero() {
        try {
            CoderResult.malformedForLength(0);
            throw new AssertionError("Expected IllegalArgumentException for length 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testMalformedForLengthNegative() {
        try {
            CoderResult.malformedForLength(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testMalformedForLengthLarge() {
        int length = 1000;
        CoderResult result = CoderResult.malformedForLength(length);
        if (result == null) {
            throw new AssertionError("Result should not be null for large length") ;
        }
        // Additional checks can be added if CoderResult exposes state
    }
}
