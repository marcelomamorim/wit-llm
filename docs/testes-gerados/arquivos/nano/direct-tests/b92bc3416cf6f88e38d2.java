/* @test    @summary Test sqrtAndRemainder method of BigInteger for correctness and edge cases    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testPerfectSquare();
        testNonPerfectSquare();
        testZero();
        testOne();
        testLargeNumber();
    }

    static void testPerfectSquare() {
        BigInteger num = BigInteger.valueOf(16); // 4^2
        BigInteger[] result = num.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        BigInteger s = result[0];
        BigInteger r = result[1];
        assert s.equals(BigInteger.valueOf(4)) : "Expected sqrt 4, got " + s;
        assert r.equals(BigInteger.ZERO) : "Expected remainder 0, got " + r;
    }

    static void testNonPerfectSquare() {
        BigInteger num = BigInteger.valueOf(20); // sqrt ~4.4721
        BigInteger[] result = num.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        BigInteger s = result[0];
        BigInteger r = result[1];
        assert s.equals(BigInteger.valueOf(4)) : "Expected sqrt 4, got " + s;
        BigInteger expectedRemainder = num.subtract(s.square());
        assert r.equals(expectedRemainder) : "Remainder mismatch";
        assert r.compareTo(BigInteger.ZERO) >= 0 : "Remainder should be non-negative";
    }

    static void testZero() {
        BigInteger num = BigInteger.ZERO;
        BigInteger[] result = num.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        assert result[0].equals(BigInteger.ZERO) : "Square root of 0 should be 0";
        assert result[1].equals(BigInteger.ZERO) : "Remainder of 0 should be 0";
    }

    static void testOne() {
        BigInteger num = BigInteger.ONE;
        BigInteger[] result = num.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        assert result[0].equals(BigInteger.ONE) : "Square root of 1 should be 1";
        assert result[1].equals(BigInteger.ZERO) : "Remainder of 1 should be 0";
    }

    static void testLargeNumber() {
        BigInteger num = new BigInteger("123456789012345678901234567890");
        BigInteger[] result = num.sqrtAndRemainder();
        assert result != null : "Result should not be null";
        BigInteger s = result[0];
        BigInteger r = result[1];
        assert s != null : "Square root result should not be null";
        assert r != null : "Remainder result should not be null";
        assert s.multiply(s).compareTo(num) <= 0 : "Square of sqrt should be <= original";
        assert s.add(BigInteger.ONE).multiply(s.add(BigInteger.ONE)).compareTo(num) > 0 : "Next integer squared should be > original";
        assert r.equals(num.subtract(s.square())) : "Remainder mismatch";
        assert r.compareTo(BigInteger.ZERO) >= 0 : "Remainder should be non-negative";
    }
}
