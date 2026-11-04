/*
 * @test
 * @summary Verify CoderResult.length() throws UnsupportedOperationException for non-error results
 * @run main CoderResultWitupTest
 */

import java.nio.charset.CoderResult;

public class CoderResultWitupTest {

    public static void main(String[] args) {
        if (CoderResult.UNDERFLOW.isError()) {
            throw new RuntimeException("UNDERFLOW must not be an error");
        }
        if (CoderResult.OVERFLOW.isError()) {
            throw new RuntimeException("OVERFLOW must not be an error");
        }
        checkThrowsUnsupported(CoderResult.UNDERFLOW, "UNDERFLOW");
        checkThrowsUnsupported(CoderResult.OVERFLOW, "OVERFLOW");
    }

    private static void checkThrowsUnsupported(CoderResult cr, String name) {
        try {
            cr.length();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException(name + ".length() did not throw UnsupportedOperationException");
    }
}
