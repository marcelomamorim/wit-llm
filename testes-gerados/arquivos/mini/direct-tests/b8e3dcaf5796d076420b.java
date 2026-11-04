/*
 * @test
 * @summary Test javax.crypto.spec.IvParameterSpec constructor with offset and length
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullIV();
        testBufferTooShort();
        testNegativeLength();
    }

    private static void testValidConstruction() {
        byte[] iv = {1, 2, 3, 4, 5};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 3);
        byte[] result = spec.getIV();
        if (result.length != 3) {
            throw new RuntimeException("Expected IV length 3 but got " + result.length);
        }
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new RuntimeException("IV bytes do not match expected subset");
        }
    }

    private static void testNullIV() {
        try {
            new IvParameterSpec(null, 0, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            if (!"IV missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null IV: " + e.getMessage());
            }
        }
    }

    private static void testBufferTooShort() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 1, 3);
            throw new RuntimeException("Expected IllegalArgumentException for buffer too short");
        } catch (IllegalArgumentException e) {
            if (!"IV buffer too short for given offset/length combination".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for buffer too short: " + e.getMessage());
            }
        }
    }

    private static void testNegativeLength() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (!"len is negative".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for negative length: " + e.getMessage());
            }
        }
    }
}
