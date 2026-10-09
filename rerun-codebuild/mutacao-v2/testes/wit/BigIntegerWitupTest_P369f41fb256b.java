/*
 * @test
 * @summary Test BigInteger.clearBit(int) for valid and invalid inputs
 * @run main BigIntegerWitupTest_P369f41fb256b
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P369f41fb256b {
    public static void main(String[] args) {
        testClearBitValid();
        testClearBitNegativeIndex();
        System.out.println("PASS");
    }

    private static void testClearBitValid() {
        // Clear bit 0 of 1 (binary 1) should yield 0
        BigInteger one = BigInteger.valueOf(1L);
        BigInteger cleared = one.clearBit(0);
        if (!cleared.equals(BigInteger.ZERO)) {
            throw new AssertionError("clearBit(0) of 1 should be 0, got: " + cleared);
        }

        // Clear bit 1 of 3 (binary 11) should yield 1 (binary 01)
        BigInteger three = BigInteger.valueOf(3L);
        BigInteger cleared1 = three.clearBit(1);
        if (!cleared1.equals(BigInteger.ONE)) {
            throw new AssertionError("clearBit(1) of 3 should be 1, got: " + cleared1);
        }

        // Clear a bit beyond current length should not throw and should extend
        BigInteger val = BigInteger.valueOf(5L); // binary 101
        BigInteger clearedHigh = val.clearBit(10);
        // Clearing a high bit that is not set should not change value
        if (!clearedHigh.equals(val)) {
            throw new AssertionError("clearBit(10) of 5 should be unchanged, got: " + clearedHigh);
        }
    }

    private static void testClearBitNegativeIndex() {
        try {
            BigInteger.ZERO.clearBit(-1);
            throw new AssertionError("clearBit with negative index did not throw ArithmeticException");
        } catch (ArithmeticException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative bit address")) {
                throw new AssertionError("ArithmeticException message unexpected: " + msg);
            }
        }
    }
}
