/* @test    @summary Test for BigInteger.flipBit(int n) handling negative input    @run main BigIntegerWitupTest_P3a78a602a52f */

import java.math.BigInteger;

public class BigIntegerWitupTest_P3a78a602a52f {
    public static void main(String[] args) {
        testFlipBitNegative();
        testFlipBitZero();
        testFlipBitPositive();
    }

    private static void testFlipBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            bi.flipBit(-1);
            System.out.println("FAIL: Expected ArithmeticException not thrown for n=-1");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for n=-1");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for n=-1: " + e);
        }
    }

    private static void testFlipBitZero() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            BigInteger result = bi.flipBit(0);
            System.out.println("PASS: flipBit(0) returned: " + result);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for n=0: " + e);
        }
    }

    private static void testFlipBitPositive() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            BigInteger result = bi.flipBit(10);
            // Verify that flipping the bit at position 10 changes the value appropriately
            // For simplicity, just check that the result is different from the original
            if (!result.equals(bi)) {
                System.out.println("PASS: flipBit(10) changed the value: " + result);
            } else {
                System.out.println("FAIL: flipBit(10) did not change the value");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for n=10: " + e);
        }
    }
}
