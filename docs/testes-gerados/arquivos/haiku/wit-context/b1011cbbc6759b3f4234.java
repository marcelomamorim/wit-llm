/*
 * @test
 * @summary Verify BigInteger.testBit two's-complement bit semantics and rejection of negative bit addresses
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.valueOf(5).testBit(0), true);
        check(BigInteger.valueOf(5).testBit(1), false);
        check(BigInteger.valueOf(5).testBit(2), true);
        check(BigInteger.valueOf(5).testBit(100), false);
        check(BigInteger.valueOf(0).testBit(0), false);
        check(BigInteger.valueOf(-1).testBit(100), true);
        check(BigInteger.valueOf(-2).testBit(0), false);
        check(BigInteger.valueOf(-2).testBit(1), true);
        check(BigInteger.valueOf(1L << 40).testBit(40), true);
        check(BigInteger.valueOf(1L << 40).testBit(39), false);
        expectArithmetic(-1);
        expectArithmetic(Integer.MIN_VALUE);
    }

    private static void check(boolean actual, boolean expected) {
        if (actual != expected) {
            throw new RuntimeException('testBit returned ' + actual + ' expected ' + expected);
        }
    }

    private static void expectArithmetic(int n) {
        try {
            BigInteger.valueOf(1).testBit(n);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException('no ArithmeticException for testBit(' + n + ')');
    }
}
