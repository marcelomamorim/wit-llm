/* @test    @summary Test for BigInteger.testBit(int n) method, especially for negative input.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testTestBitWithNegativeInput();
        testTestBitWithZeroInput();
        testTestBitWithPositiveInput();
    }

    private static void testTestBitWithNegativeInput() {
        BigInteger bi = BigInteger.valueOf(12345);
        try {
            bi.testBit(-1);
            System.out.println("FAIL: Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for negative input");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testTestBitWithZeroInput() {
        BigInteger bi = BigInteger.valueOf(12345);
        boolean result = bi.testBit(0);
        boolean expected = (bi.getInt(0 >>> 5) & (1 << (0 & 31))) != 0;
        if (result == expected) {
            System.out.println("PASS: testBit(0) returns expected result");
        } else {
            System.out.println("FAIL: testBit(0) returns unexpected result");
        }
    }

    private static void testTestBitWithPositiveInput() {
        BigInteger bi = BigInteger.valueOf(12345);
        int n = 10;
        boolean result = bi.testBit(n);
        boolean expected = (bi.getInt(n >>> 5) & (1 << (n & 31))) != 0;
        if (result == expected) {
            System.out.println("PASS: testBit(" + n + ") returns expected result");
        } else {
            System.out.println("FAIL: testBit(" + n + ") returns unexpected result");
        }
    }
}
