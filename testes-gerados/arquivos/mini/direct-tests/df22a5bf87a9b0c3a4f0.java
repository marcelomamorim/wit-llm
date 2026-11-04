/*
 * @test
 * @summary Test CoderResult.malformedForLength behavior
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {
    public static void main(String[] args) {
        testValidLengths();
        testInvalidLengthZero();
        testInvalidLengthNegative();
        testLengthGreaterThanFour();
    }

    private static void testValidLengths() {
        for (int i = 1; i <= 4; i++) {
            CoderResult cr = CoderResult.malformedForLength(i);
            if (!cr.isMalformed()) {
                throw new RuntimeException("Expected malformed result for length " + i);
            }
            if (cr.length() != i) {
                throw new RuntimeException("Expected length " + i + " but got " + cr.length());
            }
        }
    }

    private static void testInvalidLengthZero() {
        try {
            CoderResult.malformedForLength(0);
            throw new RuntimeException("Expected IllegalArgumentException for length 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidLengthNegative() {
        try {
            CoderResult.malformedForLength(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testLengthGreaterThanFour() {
        int testLength = 10;
        CoderResult cr1 = CoderResult.malformedForLength(testLength);
        CoderResult cr2 = CoderResult.malformedForLength(testLength);
        if (!cr1.isMalformed()) {
            throw new RuntimeException("Expected malformed result for length " + testLength);
        }
        if (cr1.length() != testLength) {
            throw new RuntimeException("Expected length " + testLength + " but got " + cr1.length());
        }
        if (cr1 != cr2) {
            throw new RuntimeException("Expected same instance for repeated calls with length " + testLength);
        }
    }
}
