/* @test
   @summary Tests the setBit method of BigInteger for various inputs.
   @run main BigIntegerWitupTest_P9639ffdb9fd5
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P9639ffdb9fd5 {
    public static void main(String[] args) {
        testSetBitPositive();
        testSetBitZero();
        testSetBitNegative();
        testSetBitLarge();
    }

    static void testSetBitPositive() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.setBit(0);
        assert result.equals(BigInteger.valueOf(1)) : "Failed setBit at 0";
        result = bi.setBit(5);
        assert result.equals(BigInteger.valueOf(32)) : "Failed setBit at 5";
        result = BigInteger.valueOf(8).setBit(1);
        assert result.equals(BigInteger.valueOf(10)) : "Failed setBit at 1";
    }

    static void testSetBitZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.setBit(0);
        assert result.equals(BigInteger.ONE) : "Failed setBit at 0 on zero";
    }

    static void testSetBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(1);
            bi.setBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testSetBitLarge() {
        BigInteger bi = BigInteger.valueOf(0);
        int largeBitIndex = 1000;
        BigInteger result = bi.setBit(largeBitIndex);
        BigInteger expected = BigInteger.ONE.shiftLeft(largeBitIndex);
        assert result.equals(expected) : "Failed setBit at large index";
    }
}
