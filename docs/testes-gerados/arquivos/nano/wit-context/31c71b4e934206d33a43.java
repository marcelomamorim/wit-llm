/* @test    @summary Test for java.nio.charset.CoderResult.length() method behavior.    @run main CoderResultWitupTest */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testLengthOnError();
        testLengthOnNonError();
    }

    private static void testLengthOnError() {
        CoderResult errorResult = CoderResult.malformedForLength(3);
        if (!errorResult.isError()) {
            throw new AssertionError("Expected error result to be an error.");
        }
        int length = errorResult.length();
        if (length != 3) {
            throw new AssertionError("Expected length 3, but got " + length);
        }
    }

    private static void testLengthOnNonError() {
        CoderResult underflow = CoderResult.UNDERFLOW;
        if (underflow.isError()) {
            throw new AssertionError("Expected UNDERFLOW to not be an error.");
        }
        try {
            underflow.length();
            throw new AssertionError("Expected UnsupportedOperationException for non-error result.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
