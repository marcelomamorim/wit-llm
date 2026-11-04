/*
 * @test
 * @summary Test sun.security.util.BitArray constructor with int length
 * @run main BitArrayWitupTest
 */

public class BitArrayWitupTest {
    public static void main(String[] args) {
        testNegativeLengthThrows();
        testZeroLengthCreatesEmptyArray();
        testPositiveLengthCreatesCorrectSize();
    }

    private static void testNegativeLengthThrows() {
        try {
            new sun.security.util.BitArray(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testZeroLengthCreatesEmptyArray() {
        sun.security.util.BitArray ba = new sun.security.util.BitArray(0);
        if (ba == null) {
            throw new RuntimeException("BitArray constructor returned null for length 0");
        }
    }

    private static void testPositiveLengthCreatesCorrectSize() {
        int length = 10;
        sun.security.util.BitArray ba = new sun.security.util.BitArray(length);
        if (ba == null) {
            throw new RuntimeException("BitArray constructor returned null for positive length");
        }
        // The internal repn array length should be (length + 7) / 8
        int expectedRepnLength = (length + 7) / 8;
        try {
            java.lang.reflect.Field repnField = sun.security.util.BitArray.class.getDeclaredField("repn");
            repnField.setAccessible(true);
            byte[] repn = (byte[]) repnField.get(ba);
            if (repn.length != expectedRepnLength) {
                throw new RuntimeException("Internal repn array length incorrect: expected " + expectedRepnLength + ", got " + repn.length);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to access repn field", e);
        }
    }
}
