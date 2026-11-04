/*
 * @test
 * @summary Witup-derived checks for BigInteger.clearBit(int): negative bit address throws ArithmeticException and clearing bits yields expected values
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeBitAddressThrows();
        testNegativeBitAddressMinValueThrows();
        testClearLowBit();
        testClearBitAlreadyClear();
        testClearBitOfOne();
        testClearBitNegativeOperand();
        testClearBitBeyondLength();
        testClearHighBitOfPowerOfTwo();
    }

    static void testNegativeBitAddressThrows() {
        boolean thrown = false;
        try {
            BigInteger.valueOf(1).clearBit(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "clearBit(-1) should throw ArithmeticException");
    }

    static void testNegativeBitAddressMinValueThrows() {
        boolean thrown = false;
        try {
            BigInteger.valueOf(0).clearBit(Integer.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "clearBit(Integer.MIN_VALUE) should throw ArithmeticException");
    }

    static void testClearLowBit() {
        BigInteger r = BigInteger.valueOf(255).clearBit(0);
        check(r.equals(BigInteger.valueOf(254)), "255 clearBit(0) expected 254 but was " + r);
    }

    static void testClearBitAlreadyClear() {
        BigInteger r = BigInteger.valueOf(5).clearBit(1);
        check(r.equals(BigInteger.valueOf(5)), "5 clearBit(1) expected 5 but was " + r);
    }

    static void testClearBitOfOne() {
        BigInteger r = BigInteger.valueOf(1).clearBit(0);
        check(r.equals(BigInteger.valueOf(0)), "1 clearBit(0) expected 0 but was " + r);
    }

    static void testClearBitNegativeOperand() {
        BigInteger r = BigInteger.valueOf(-1).clearBit(0);
        check(r.equals(BigInteger.valueOf(-2)), "-1 clearBit(0) expected -2 but was " + r);
    }

    static void testClearBitBeyondLength() {
        BigInteger r = BigInteger.valueOf(7).clearBit(100);
        check(r.equals(BigInteger.valueOf(7)), "7 clearBit(100) expected 7 but was " + r);
    }

    static void testClearHighBitOfPowerOfTwo() {
        BigInteger r = BigInteger.valueOf(1L << 40).clearBit(40);
        check(r.equals(BigInteger.valueOf(0)), "2^40 clearBit(40) expected 0 but was " + r);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
