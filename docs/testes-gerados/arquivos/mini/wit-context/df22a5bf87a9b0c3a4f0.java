/*
 * @test
 * @summary Test java.nio.charset.CoderResult.malformedForLength(int) method
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {
    public static void main(String[] args) {
        testMalformedForLengthValid();
        testMalformedForLengthInvalid();
    }

    private static void testMalformedForLengthValid() {
        // Test valid lengths 1 to 4 return non-null results
        for (int length = 1; length <= 4; length++) {
            CoderResult result = CoderResult.malformedForLength(length);
            if (result == null) {
                throw new AssertionError("malformedForLength(" + length + ") returned null");
            }
            if (!result.isMalformed()) {
                throw new AssertionError("malformedForLength(" + length + ") did not return a malformed result");
            }
            if (result.length() != length) {
                throw new AssertionError("malformedForLength(" + length + ") returned result with wrong length: " + result.length());
            }
        }

        // Test a length > 4 returns a non-null malformed result
        int testLength = 10;
        CoderResult result = CoderResult.malformedForLength(testLength);
        if (result == null) {
            throw new AssertionError("malformedForLength(" + testLength + ") returned null");
        }
        if (!result.isMalformed()) {
            throw new AssertionError("malformedForLength(" + testLength + ") did not return a malformed result");
        }
        if (result.length() != testLength) {
            throw new AssertionError("malformedForLength(" + testLength + ") returned result with wrong length: " + result.length());
        }
    }

    private static void testMalformedForLengthInvalid() {
        // Test that length <= 0 throws IllegalArgumentException
        int[] invalidLengths = {0, -1, -10};
        for (int length : invalidLengths) {
            boolean thrown = false;
            try {
                CoderResult.malformedForLength(length);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("malformedForLength(" + length + ") did not throw IllegalArgumentException");
            }
        }
    }
}
