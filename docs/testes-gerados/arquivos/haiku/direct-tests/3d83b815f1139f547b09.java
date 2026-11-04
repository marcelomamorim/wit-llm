/*
 * @test
 * @summary Black-box checks for BigInteger.flipBit including negative bit address and involution
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void checkEquals(BigInteger expected, BigInteger actual, String msg) {
        check(expected.equals(actual), msg + " expected " + expected + " got " + actual);
    }

    public static void main(String[] args) {
        boolean thrown = false;
        try {
            BigInteger.ONE.flipBit(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "negative bit address must throw ArithmeticException");

        checkEquals(BigInteger.ONE, BigInteger.ZERO.flipBit(0), "zero flip bit 0");
        checkEquals(BigInteger.valueOf(-2), BigInteger.valueOf(-1).flipBit(0), "-1 flip bit 0");
        checkEquals(BigInteger.valueOf(7), BigInteger.valueOf(5).flipBit(1), "5 flip bit 1");
        checkEquals(BigInteger.valueOf(1L << 31), BigInteger.ZERO.flipBit(31), "zero flip bit 31");
        checkEquals(BigInteger.valueOf(1L << 32), BigInteger.ZERO.flipBit(32), "zero flip bit 32");
        checkEquals(BigInteger.valueOf(-1L - (1L << 40)), BigInteger.valueOf(-1).flipBit(40), "-1 flip bit 40");
        checkEquals(BigInteger.ONE.shiftLeft(100), BigInteger.ZERO.flipBit(100), "zero flip bit 100");

        long[] values = {0L, 1L, -1L, 12345L, -987654321987L, Long.MAX_VALUE};
        for (long v : values) {
            BigInteger x = BigInteger.valueOf(v);
            for (int n = 0; n <= 200; n++) {
                BigInteger once = x.flipBit(n);
                check(!once.equals(x), "flipBit must change value, v=" + v + " n=" + n);
                check(once.testBit(n) != x.testBit(n), "flipBit must toggle target bit, v=" + v + " n=" + n);
                checkEquals(x, once.flipBit(n), "double flip must restore, v=" + v + " n=" + n);
            }
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
