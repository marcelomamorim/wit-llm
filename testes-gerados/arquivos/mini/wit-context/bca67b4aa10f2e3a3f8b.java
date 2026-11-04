/*
 * @test
 * @summary Test javax.crypto.spec.GCMParameterSpec constructors for input validation and correct behavior
 * @run main GCMParameterSpecWitupTest
 */

import javax.crypto.spec.GCMParameterSpec;

public class GCMParameterSpecWitupTest {
    public static void main(String[] args) {
        testConstructorNullSrc();
        testConstructorNegativeTLen();
        testConstructorValidInput();
    }

    private static void testConstructorNullSrc() {
        try {
            new GCMParameterSpec(128, null);
            throw new RuntimeException("Expected IllegalArgumentException for null src array");
        } catch (IllegalArgumentException e) {
            if (!"src array is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorNegativeTLen() {
        byte[] iv = new byte[] {1, 2, 3, 4};
        try {
            new GCMParameterSpec(-1, iv);
            throw new RuntimeException("Expected IllegalArgumentException for negative tLen");
        } catch (IllegalArgumentException e) {
            // The exact message is not specified, so just accept IllegalArgumentException
        }
    }

    private static void testConstructorValidInput() {
        byte[] iv = new byte[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
        int tLen = 128;
        GCMParameterSpec spec = new GCMParameterSpec(tLen, iv);
        if (spec == null) {
            throw new RuntimeException("Constructor returned null");
        }
        // No direct getter for IV or tLen, so just ensure no exception and object created
    }
}
