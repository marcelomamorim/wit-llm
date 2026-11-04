/*
 * @test
 * @summary Tests observable behavior of BigInteger.clearBit
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.valueOf(15).clearBit(0), BigInteger.valueOf(14));
        check(BigInteger.valueOf(15).clearBit(3), BigInteger.valueOf(7));
        check(BigInteger.valueOf(15).clearBit(10), BigInteger.valueOf(15));
        check(BigInteger.valueOf(0xFF).clearBit(3), BigInteger.valueOf(0xF7));
        check(BigInteger.ZERO.clearBit(5), BigInteger.ZERO);
        check(BigInteger.valueOf(-1).clearBit(0), BigInteger.valueOf(-2));
        check(BigInteger.valueOf(-1).clearBit(40), new BigInteger("-1099511627777"));
        check(BigInteger.valueOf(1L << 40).clearBit(40), BigInteger.ZERO);

        if (BigInteger.valueOf(15).clearBit(3).testBit(3)) {
            throw new RuntimeException("bit 3 should be cleared");
        }
        if (BigInteger.valueOf(-1).clearBit(0).testBit(0)) {
            throw new RuntimeException("bit 0 should be cleared for -1");
        }

        boolean thrown = false;
        try {
            BigInteger.ONE.clearBit(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("negative bit address must throw ArithmeticException");
        }
    }

    private static void check(BigInteger actual, BigInteger expected) {
        if (!actual.equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
