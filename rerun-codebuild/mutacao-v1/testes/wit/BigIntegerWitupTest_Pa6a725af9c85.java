/* @test    @summary Test setBit with valid and invalid inputs    @run main BigIntegerWitupTest_Pa6a725af9c85 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pa6a725af9c85 {

    public static void main(String[] args) {
        testSetBitValid();
        testSetBitNegative();
    }

    static void testSetBitValid() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            BigInteger result = bi.setBit(0);
            if (result == null) {
                throw new AssertionError("setBit returned null for n=0");
            }
            BigInteger expected = BigInteger.valueOf(1);
            if (!result.equals(expected)) {
                throw new AssertionError("Expected 1, got " + result);
            }
            // Test setting a higher bit
            BigInteger bi2 = BigInteger.valueOf(0);
            BigInteger result2 = bi2.setBit(31);
            BigInteger expected2 = BigInteger.valueOf(1).shiftLeft(31);
            if (!result2.equals(expected2)) {
                throw new AssertionError("Expected " + expected2 + ", got " + result2);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testSetBitValid: " + e);
        }
    }

    static void testSetBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            bi.setBit(-1);
            throw new AssertionError("Expected ArithmeticException for n=-1");
        } catch (ArithmeticException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
