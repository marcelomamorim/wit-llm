/*
 * @test
 * @summary Test CoderResult.length() behavior including exception on non-error results
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        testLengthThrowsOnNonError();
        testLengthReturnsValueOnError();
    }

    private static void testLengthThrowsOnNonError() {
        // UNDERFLOW and OVERFLOW are not errors, length() should throw UnsupportedOperationException
        try {
            CoderResult.UNDERFLOW.length();
            throw new RuntimeException("Expected UnsupportedOperationException for UNDERFLOW");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        try {
            CoderResult.OVERFLOW.length();
            throw new RuntimeException("Expected UnsupportedOperationException for OVERFLOW");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void testLengthReturnsValueOnError() {
        // Malformed and Unmappable are errors, length() should return the length value
        CoderResult malformed = CoderResult.malformedForLength(3);
        if (!malformed.isMalformed()) {
            throw new RuntimeException("Expected malformed result");
        }
        if (malformed.length() != 3) {
            throw new RuntimeException("Malformed length() returned " + malformed.length() + ", expected 3");
        }

        CoderResult unmappable = CoderResult.unmappableForLength(5);
        if (!unmappable.isUnmappable()) {
            throw new RuntimeException("Expected unmappable result");
        }
        if (unmappable.length() != 5) {
            throw new RuntimeException("Unmappable length() returned " + unmappable.length() + ", expected 5");
        }
    }
}
