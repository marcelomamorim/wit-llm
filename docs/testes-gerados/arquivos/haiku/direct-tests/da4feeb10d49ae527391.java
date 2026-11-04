/*
 * @test
 * @summary Basic behavior of the public BitArray(int length) constructor
 * @modules java.base/sun.security.util
 * @run main BitArrayWitupTest
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testValidLengthsDoNotThrow();
        testNewBitsAreZero();
        testNegativeLengthThrowsWithMessage();
        testMinIntegerLengthThrows();
    }

    private static void testValidLengthsDoNotThrow() {
        int[] lengths = {0, 1, 7, 8, 9, 16, 17};
        for (int len : lengths) {
            BitArray ba = new BitArray(len);
            check(ba != null, "BitArray should be created for length " + len);
        }
    }

    private static void testNewBitsAreZero() {
        BitArray one = new BitArray(1);
        check(!one.get(0), "Bit 0 of new BitArray(1) should be false");

        BitArray nine = new BitArray(9);
        check(!nine.get(0), "Bit 0 of new BitArray(9) should be false");
        check(!nine.get(8), "Bit 8 of new BitArray(9) should be false");
    }

    private static void testNegativeLengthThrowsWithMessage() {
        try {
            new BitArray(-1);
            throw new RuntimeException("Expected IllegalArgumentException for length -1");
        } catch (IllegalArgumentException e) {
            check("Negative length for BitArray".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    private static void testMinIntegerLengthThrows() {
        try {
            new BitArray(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check("Negative length for BitArray".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }
}
