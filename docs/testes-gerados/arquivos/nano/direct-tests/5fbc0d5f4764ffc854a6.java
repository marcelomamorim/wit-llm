/* @test    @summary Test BigInteger.divide for basic division behavior    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testDividePositiveNumbers();
        testDivideNegativeNumbers();
        testDivideByZero();
        testDivideZeroNumerator();
        testDivideLargeNumbers();
    }

    static void testDividePositiveNumbers() {
        BigInteger a = BigInteger.valueOf(100);
        BigInteger b = BigInteger.valueOf(20);
        BigInteger result = a.divide(b);
        assert result.equals(BigInteger.valueOf(5)) : "Expected 5, got " + result;
    }

    static void testDivideNegativeNumbers() {
        BigInteger a = BigInteger.valueOf(-100);
        BigInteger b = BigInteger.valueOf(20);
        BigInteger result = a.divide(b);
        assert result.equals(BigInteger.valueOf(-5)) : "Expected -5, got " + result;
        BigInteger c = BigInteger.valueOf(100);
        BigInteger d = BigInteger.valueOf(-20);
        BigInteger result2 = c.divide(d);
        assert result2.equals(BigInteger.valueOf(-5)) : "Expected -5, got " + result2;
        BigInteger e = BigInteger.valueOf(-100);
        BigInteger f = BigInteger.valueOf(-20);
        BigInteger result3 = e.divide(f);
        assert result3.equals(BigInteger.valueOf(5)) : "Expected 5, got " + result3;
    }

    static void testDivideByZero() {
        BigInteger a = BigInteger.valueOf(100);
        try {
            a.divide(BigInteger.ZERO);
            assert false : "Expected ArithmeticException";
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testDivideZeroNumerator() {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(12345);
        BigInteger result = a.divide(b);
        assert result.equals(BigInteger.ZERO) : "Expected 0, got " + result;
    }

    static void testDivideLargeNumbers() {
        BigInteger a = new BigInteger("123456789012345678901234567890");
        BigInteger b = new BigInteger("9876543210");
        BigInteger result = a.divide(b);
        BigInteger expected = new BigInteger("12499999999");
        assert result.equals(expected) : "Expected " + expected + ", got " + result;
    }
}
