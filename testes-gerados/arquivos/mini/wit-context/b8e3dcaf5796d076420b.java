/*
 * @test
 * @summary Tests for javax.crypto.spec.IvParameterSpec(byte[] iv, int offset, int len) constructor
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {
    public static void main(String[] args) {
        testNullIVThrows();
        testBufferTooShortThrows();
        testNegativeLenThrows();
        testValidConstructionCopiesArray();
    }

    private static void testNullIVThrows() {
        try {
            new IvParameterSpec(null, 0, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null iv");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testBufferTooShortThrows() {
        byte[] iv = new byte[5];
        try {
            new IvParameterSpec(iv, 3, 3); // 5 - 3 < 3
            throw new RuntimeException("Expected IllegalArgumentException for buffer too short");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeLenThrows() {
        byte[] iv = new byte[5];
        try {
            new IvParameterSpec(iv, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative len");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testValidConstructionCopiesArray() {
        byte[] iv = {1, 2, 3, 4, 5};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 3);
        byte[] returnedIV = spec.getIV();

        if (returnedIV.length != 3) {
            throw new RuntimeException("Returned IV length mismatch");
        }
        if (returnedIV[0] != 2 || returnedIV[1] != 3 || returnedIV[2] != 4) {
            throw new RuntimeException("Returned IV content mismatch");
        }

        // Modify original array and check that spec's IV is not affected
        iv[1] = 99;
        byte[] returnedIV2 = spec.getIV();
        if (returnedIV2[0] != 2) {
            throw new RuntimeException("IV was not copied defensively");
        }
    }
}
