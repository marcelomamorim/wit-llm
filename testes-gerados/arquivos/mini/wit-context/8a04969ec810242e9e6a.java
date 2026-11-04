/*
 * @test
 * @summary Test constructor ECFieldF2m(int m) for valid and invalid inputs
 * @run main ECFieldF2mWitupTest
 */

import java.security.spec.ECFieldF2m;

public class ECFieldF2mWitupTest {
    public static void main(String[] args) {
        testValidM();
        testInvalidM();
        System.out.println("All tests passed.");
    }

    private static void testValidM() {
        // Test with a positive m value
        int m = 10;
        ECFieldF2m field = new ECFieldF2m(m);
        if (field.getM() != m) {
            throw new AssertionError("Expected m=" + m + ", got " + field.getM());
        }
        if (field.getMidTermsOfReductionPolynomial() != null) {
            throw new AssertionError("Expected ks to be null for normal basis");
        }
        if (field.getReductionPolynomial() != null) {
            throw new AssertionError("Expected rp to be null for normal basis");
        }
    }

    private static void testInvalidM() {
        // Test with zero and negative m values, expect IllegalArgumentException
        int[] invalidMs = {0, -1, -100};
        for (int m : invalidMs) {
            boolean thrown = false;
            try {
                new ECFieldF2m(m);
            } catch (IllegalArgumentException e) {
                if (!"m is not positive".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage());
                }
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for m=" + m);
            }
        }
    }
}
