/* @test
   @summary Tests BigInteger.setBit for valid and invalid inputs.
   @run main BigIntegerWitupTest_P9639ffdb9fd5
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P9639ffdb9fd5 {
    public static void main(String[] args) {
        testSetBitNegative();
        testSetBitZero();
        testSetBitPositive();
    }
    static void testSetBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            bi.setBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            // expected
        }
    }
    static void testSetBitZero() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.setBit(0);
        if (!result.equals(BigInteger.ONE)) {
            throw new AssertionError("setBit(0) should set least significant bit");
        }
    }
    static void testSetBitPositive() {
        BigInteger bi = BigInteger.valueOf(2); // binary 10
        BigInteger result = bi.setBit(2); // set bit at position 2 (zero-based)
        BigInteger expected = BigInteger.valueOf(2 + 4); // 10 + 100 = 110 in binary
        if (!result.equals(expected)) {
            throw new AssertionError("setBit(2) failed: expected " + expected + " but got " + result);
        }
    }
}
