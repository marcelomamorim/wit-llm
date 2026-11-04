/*
 * @test
 * @summary Test java.nio.charset.CoderResult.length() method behavior
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testLengthOnErrorResult();
        testLengthOnNonErrorResult();
    }

    private static void testLengthOnErrorResult() {
        // Create a malformed CoderResult with length 3
        CoderResult cr = CoderResult.malformedForLength(3);
        int len = cr.length();
        if (len != 3) {
            throw new AssertionError("Expected length 3 but got " + len);
        }
    }

    private static void testLengthOnNonErrorResult() {
        // Use the UNDERFLOW singleton which is not an error
        CoderResult cr = CoderResult.UNDERFLOW;
        try {
            cr.length();
            throw new AssertionError("Expected UnsupportedOperationException for non-error result");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
