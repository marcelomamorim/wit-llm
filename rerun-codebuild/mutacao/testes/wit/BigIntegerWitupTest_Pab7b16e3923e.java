/* @test    @summary Test setBit method for BigInteger, focusing on exception path for negative input    @run main BigIntegerWitupTest_Pab7b16e3923e */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pab7b16e3923e {

    public static void main(String[] args) {
        testSetBitNegative();
        testSetBitZero();
        testSetBitPositive();
    }

    private static void testSetBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            bi.setBit(-1);
            System.err.println("Failed: Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            System.out.println("Passed: ArithmeticException thrown for negative n");
        }
    }

    private static void testSetBitZero() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            BigInteger result = bi.setBit(0);
            if (result != null) {
                System.out.println("Passed: setBit(0) executed without exception");
            } else {
                System.err.println("Failed: setBit(0) returned null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for n=0: " + e);
        }
    }

    private static void testSetBitPositive() {
        try {
            BigInteger bi = BigInteger.valueOf(0);
            BigInteger result = bi.setBit(5);
            if (result != null && result.testBit(5)) {
                System.out.println("Passed: setBit(5) set the bit correctly");
            } else {
                System.err.println("Failed: setBit(5) did not set the bit correctly");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for n=5: " + e);
        }
    }
}
