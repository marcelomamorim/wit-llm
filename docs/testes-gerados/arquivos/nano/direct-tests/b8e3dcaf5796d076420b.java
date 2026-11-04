/* @test    @summary Test constructor IvParameterSpec(byte[] iv, int offset, int len) for correct behavior and exception handling    @run main IvParameterSpecWitupTest */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidConstruction();
        testNullIvThrows();
        testShortBufferThrows();
        testNegativeLengthThrows();
    }

    static void testValidConstruction() {
        byte[] iv = {1, 2, 3, 4, 5};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 3);
        byte[] result = spec.getIV();
        assert result.length == 3 : "Expected length 3";
        assert result[0] == 2 : "Expected byte 2";
        assert result[1] == 3 : "Expected byte 3";
        assert result[2] == 4 : "Expected byte 4";
    }

    static void testNullIvThrows() {
        try {
            new IvParameterSpec(null, 0, 1);
            assert false : "Expected IllegalArgumentException for null iv";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("IV missing") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testShortBufferThrows() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 2, 2);
            assert false : "Expected IllegalArgumentException for buffer too short";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("IV buffer too short") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testNegativeLengthThrows() {
        byte[] iv = {1, 2, 3};
        try {
            new IvParameterSpec(iv, 0, -1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative length";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert e.getMessage().equals("len is negative") : "Unexpected message: " + e.getMessage();
        }
    }
}
