/* @test    @summary Test for BigInteger.clearBit(int n) handling negative input    @run main BigIntegerWitupTest_Pa57d6d3150a1 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pa57d6d3150a1 {

    public static void main(String[] args) {
        testClearBitNegative();
        testClearBitPositive();
        testClearBitZero();
    }

    private static void testClearBitNegative() {
        BigInteger bi = BigInteger.valueOf(12345L);
        try {
            bi.clearBit(-1);
            System.out.println("FAIL: Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for negative n");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testClearBitPositive() {
        BigInteger bi = new BigInteger("1024"); // 2^10
        BigInteger result = bi.clearBit(10); // should clear the 11th bit (index 10)
        if (result.equals(BigInteger.valueOf(1024 - (1 << 10)))) {
            System.out.println("PASS: clearBit(10) cleared the 11th bit as expected");
        } else {
            System.out.println("FAIL: clearBit(10) did not produce expected result");
        }
    }

    private static void testClearBitZero() {
        BigInteger bi = new BigInteger("15"); // binary 1111
        BigInteger result = bi.clearBit(0); // clear least significant bit
        if (result.equals(new BigInteger("14"))) {
            System.out.println("PASS: clearBit(0) cleared the least significant bit");
        } else {
            System.out.println("FAIL: clearBit(0) did not produce expected result");
        }
    }
}
