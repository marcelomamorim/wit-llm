/*
 * @test
 * @summary Test CoderResult.unmappableForLength(int) for valid and invalid inputs
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {
    public static void main(String[] args) {
        testUnmappableForLengthValid();
        testUnmappableForLengthInvalid();
        System.out.println("All tests passed.");
    }

    private static void testUnmappableForLengthValid() {
        // Test valid lengths 1 to 4 return non-null singleton instances
        for (int length = 1; length <= 4; length++) {
            CoderResult result = CoderResult.unmappableForLength(length);
            if (result == null) {
                throw new AssertionError("unmappableForLength(" + length + ") returned null");
            }
            if (!result.isUnmappable()) {
                throw new AssertionError("Result for length " + length + " is not unmappable");
            }
            if (result.length() != length) {
                throw new AssertionError("Result length mismatch: expected " + length + ", got " + result.length());
            }
        }

        // Test a length > 4 returns a non-null CoderResult with correct length and unmappable true
        int testLength = 10;
        CoderResult result = CoderResult.unmappableForLength(testLength);
        if (result == null) {
            throw new AssertionError("unmappableForLength(" + testLength + ") returned null");
        }
        if (!result.isUnmappable()) {
            throw new AssertionError("Result for length " + testLength + " is not unmappable");
        }
        if (result.length() != testLength) {
            throw new AssertionError("Result length mismatch: expected " + testLength + ", got " + result.length());
        }

        // Test that repeated calls with same length > 4 return equal results (not necessarily same instance)
        CoderResult result2 = CoderResult.unmappableForLength(testLength);
        if (!result.equals(result2)) {
            throw new AssertionError("Repeated calls with same length should return equal results");
        }
    }

    private static void testUnmappableForLengthInvalid() {
        // Test that length <= 0 throws IllegalArgumentException
        int[] invalidLengths = {0, -1, -10};
        for (int length : invalidLengths) {
            boolean thrown = false;
            try {
                CoderResult.unmappableForLength(length);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for length " + length);
            }
        }
    }
}
