/*
 * @test
 * @summary Test ECFieldF2m(int m) constructor for valid and invalid inputs
 * @run main ECFieldF2mWitupTest
 */

import java.security.spec.ECFieldF2m;

public class ECFieldF2mWitupTest {
    public static void main(String[] args) {
        testValidPositiveM();
        testZeroMThrows();
        testNegativeMThrows();
    }

    private static void testValidPositiveM() {
        ECFieldF2m field = new ECFieldF2m(1);
        if (field == null) {
            throw new RuntimeException("Constructor returned null for m=1");
        }
        // No exception expected, so test passes if reached here
    }

    private static void testZeroMThrows() {
        try {
            new ECFieldF2m(0);
            throw new RuntimeException("Expected IllegalArgumentException for m=0");
        } catch (IllegalArgumentException e) {
            if (!"m is not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for m=0: " + e.getMessage());
            }
        }
    }

    private static void testNegativeMThrows() {
        try {
            new ECFieldF2m(-1);
            throw new RuntimeException("Expected IllegalArgumentException for m=-1");
        } catch (IllegalArgumentException e) {
            if (!"m is not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for m=-1: " + e.getMessage());
            }
        }
    }
}
