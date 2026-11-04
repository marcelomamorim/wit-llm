/*
 * @test
 * @summary Test BigInteger.testBit(int) for valid and invalid inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testTestBitValid();
        testTestBitNegativeIndex();
        System.out.println("All tests passed.");
    }

    private static void testTestBitValid() {
        // Test with bit index 0 (lowest bit)
        BigInteger zero = BigInteger.ZERO;
        if (zero.testBit(0)) {
            throw new AssertionError("ZERO.testBit(0) should be false");
        }

        // Test with bit index 1 (second bit)
        BigInteger two = BigInteger.valueOf(2L); // binary 10
        if (!two.testBit(1)) {
            throw new AssertionError("2.testBit(1) should be true");
        }
        if (two.testBit(0)) {
            throw new AssertionError("2.testBit(0) should be false");
        }

        // Test with a larger bit index that is set
        BigInteger big = BigInteger.valueOf(1L << 31);
        if (!big.testBit(31)) {
            throw new AssertionError("big.testBit(31) should be true");
        }

        // Test with a bit index that is not set
        if (big.testBit(30)) {
            throw new AssertionError("big.testBit(30) should be false");
        }
    }

    private static void testTestBitNegativeIndex() {
        BigInteger val = BigInteger.ONE;
        try {
            val.testBit(-1);
            throw new AssertionError("testBit(-1) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative bit address")) {
                throw new AssertionError("ArithmeticException message unexpected: " + msg);
            }
        }
    }
}
