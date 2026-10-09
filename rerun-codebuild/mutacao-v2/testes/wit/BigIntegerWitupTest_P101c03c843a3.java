/*
 * @test
 * @summary Test java.math.BigInteger.setBit(int) for valid and invalid inputs
 * @run main BigIntegerWitupTest_P101c03c843a3
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P101c03c843a3 {
    public static void main(String[] args) {
        testSetBitValid();
        testSetBitNegativeIndex();
        System.out.println("All tests passed.");
    }

    private static void testSetBitValid() {
        // Test setting bit 0 on zero
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = zero.setBit(0);
        if (!one.equals(BigInteger.ONE)) {
            throw new AssertionError("setBit(0) on zero should be 1, got: " + one);
        }

        // Test setting a higher bit
        BigInteger base = BigInteger.valueOf(0x10); // 16 decimal
        BigInteger result = base.setBit(5); // bit 5 is already set in 16 (2^4), bit 5 is 2^5=32
        BigInteger expected = BigInteger.valueOf(0x30); // 16 + 32 = 48
        if (!result.equals(expected)) {
            throw new AssertionError("setBit(5) on 16 should be 48, got: " + result);
        }

        // Test setting a bit beyond current length
        BigInteger small = BigInteger.valueOf(1);
        BigInteger bigBit = small.setBit(100);
        if (!bigBit.testBit(100)) {
            throw new AssertionError("setBit(100) should set bit 100");
        }
    }

    private static void testSetBitNegativeIndex() {
        try {
            BigInteger.ZERO.setBit(-1);
            throw new AssertionError("setBit(-1) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Negative bit address")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
