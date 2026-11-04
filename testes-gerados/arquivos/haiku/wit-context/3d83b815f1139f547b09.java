/*
 * @test
 * @summary Witup-derived tests for BigInteger.flipBit
 * @run main BigIntegerWitupTest
 */

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeBitAddressThrows();
        testFlipOnZero();
        testDoubleFlipRestoresOriginal();
        testFlipHighBitOfZero();
        testFlipBitOfNegativeValue();
        testFlipSetsObservableBit();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeBitAddressThrows() {
        boolean thrown = false;
        try {
            BigInteger.valueOf(5).flipBit(-1);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "flipBit(-1) must throw ArithmeticException");
    }

    static void testFlipOnZero() {
        check(BigInteger.ZERO.flipBit(0).equals(BigInteger.ONE),
              "flipBit(0) on ZERO must yield ONE");
    }

    static void testDoubleFlipRestoresOriginal() {
        BigInteger original = BigInteger.valueOf(12345);
        check(original.flipBit(7).flipBit(7).equals(original),
              "flipping the same bit twice must restore the original value");
    }

    static void testFlipHighBitOfZero() {
        check(BigInteger.ZERO.flipBit(40).equals(BigInteger.valueOf(1L << 40)),
              "flipBit(40) on ZERO must yield 2^40");
    }

    static void testFlipBitOfNegativeValue() {
        check(BigInteger.valueOf(-1).flipBit(3).equals(BigInteger.valueOf(-9)),
              "flipBit(3) on -1 must yield -9 in two's-complement semantics");
    }

    static void testFlipSetsObservableBit() {
        check(BigInteger.valueOf(5).flipBit(1).testBit(1),
              "flipBit(1) on 5 must set bit 1");
    }
}
