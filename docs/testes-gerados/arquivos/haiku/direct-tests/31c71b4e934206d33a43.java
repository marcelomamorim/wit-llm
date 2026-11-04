/*
 * @test
 * @summary Verifies CoderResult.length() returns the error length for malformed and unmappable results and throws UnsupportedOperationException for non-error results
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) throws Exception {
        testMalformedLength();
        testUnmappableLength();
        testUnderflowThrows();
        testOverflowThrows();
    }

    static void testMalformedLength() {
        CoderResult cr = CoderResult.malformedForLength(3);
        if (!cr.isError() || !cr.isMalformed()) {
            throw new RuntimeException("expected malformed error");
        }
        if (cr.length() != 3) {
            throw new RuntimeException("expected length 3, got " + cr.length());
        }
    }

    static void testUnmappableLength() {
        CoderResult cr = CoderResult.unmappableForLength(2);
        if (!cr.isError() || !cr.isUnmappable()) {
            throw new RuntimeException("expected unmappable error");
        }
        if (cr.length() != 2) {
            throw new RuntimeException("expected length 2, got " + cr.length());
        }
    }

    static void testUnderflowThrows() {
        boolean thrown = false;
        try {
            CoderResult.UNDERFLOW.length();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("UNDERFLOW.length() did not throw UnsupportedOperationException");
        }
    }

    static void testOverflowThrows() {
        boolean thrown = false;
        try {
            CoderResult.OVERFLOW.length();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("OVERFLOW.length() did not throw UnsupportedOperationException");
        }
    }
}
