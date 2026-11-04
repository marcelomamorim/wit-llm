/*
 * @test
 * @summary Verifies BigInteger.testBit for positive, zero, negative (two's-complement) values, bits beyond the int boundary, and negative bit addresses.
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        // Positive values
        check(BigInteger.valueOf(5).testBit(0), true, "5 bit0");
        check(BigInteger.valueOf(5).testBit(1), false, "5 bit1");
        check(BigInteger.valueOf(5).testBit(2), true, "5 bit2");
        check(BigInteger.valueOf(5).testBit(3), false, "5 bit3");
        check(BigInteger.valueOf(5).testBit(1000), false, "5 high bit");

        // Zero has no set bits
        check(BigInteger.valueOf(0).testBit(0), false, "0 bit0");
        check(BigInteger.valueOf(0).testBit(31), false, "0 bit31");
        check(BigInteger.valueOf(0).testBit(500), false, "0 bit500");

        // Negative values use two's-complement semantics
        check(BigInteger.valueOf(-1).testBit(0), true, "-1 bit0");
        check(BigInteger.valueOf(-1).testBit(100), true, "-1 bit100");
        check(BigInteger.valueOf(-2).testBit(0), false, "-2 bit0");
        check(BigInteger.valueOf(-2).testBit(1), true, "-2 bit1");
        check(BigInteger.valueOf(-8).testBit(2), false, "-8 bit2");
        check(BigInteger.valueOf(-8).testBit(3), true, "-8 bit3");
        check(new BigInteger("-5").testBit(0), true, "-5 bit0");
        check(new BigInteger("-5").testBit(2), false, "-5 bit2");

        // Bits across the 32-bit word boundary
        check(BigInteger.valueOf(1L << 31).testBit(31), true, "2^31 bit31");
        check(BigInteger.valueOf(1L << 31).testBit(30), false, "2^31 bit30");
        check(BigInteger.valueOf(1L << 32).testBit(32), true, "2^32 bit32");
        check(BigInteger.valueOf(1L << 32).testBit(31), false, "2^32 bit31");
        check(BigInteger.valueOf(1L << 40).testBit(40), true, "2^40 bit40");
        check(BigInteger.valueOf(1L << 40).testBit(39), false, "2^40 bit39");

        // Long.MIN_VALUE: only bit 63 set among low bits, sign extension above
        check(BigInteger.valueOf(Long.MIN_VALUE).testBit(63), true, "MIN bit63");
        check(BigInteger.valueOf(Long.MIN_VALUE).testBit(62), false, "MIN bit62");
        check(BigInteger.valueOf(Long.MIN_VALUE).testBit(200), true, "MIN bit200");

        // Negative bit address must throw ArithmeticException
        checkNegativeBitThrows(BigInteger.valueOf(5), -1);
        checkNegativeBitThrows(BigInteger.valueOf(0), -1);
        checkNegativeBitThrows(BigInteger.valueOf(-7), Integer.MIN_VALUE);
    }

    static void check(boolean actual, boolean expected, String msg) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected result for " + msg + ": " + actual);
        }
    }

    static void checkNegativeBitThrows(BigInteger value, int n) {
        try {
            value.testBit(n);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException for n=" + n);
    }
}
