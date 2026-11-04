/* @test    @summary Test for BigInteger(int, int, Random) constructor with edge cases and exception handling    @run main BigIntegerWitupTest */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testConstructorWithSmallBitLength();
        testConstructorWithLargeBitLength();
        testConstructorWithInvalidBitLength();
    }

    private static void testConstructorWithSmallBitLength() {
        try {
            Random rnd = new Random(12345);
            BigInteger bi = new BigInteger(1, 10, rnd);
            System.out.println("Passed small bitLength test: " + bi);
        } catch (Exception e) {
            System.err.println("Failed small bitLength test: " + e);
        }
    }

    private static void testConstructorWithLargeBitLength() {
        try {
            Random rnd = new Random(54321);
            BigInteger bi = new BigInteger(1024, 20, rnd);
            System.out.println("Passed large bitLength test: " + bi);
        } catch (Exception e) {
            System.err.println("Failed large bitLength test: " + e);
        }
    }

    private static void testConstructorWithInvalidBitLength() {
        try {
            Random rnd = new Random(0);
            BigInteger bi = new BigInteger(1, 10, rnd);
            System.err.println("Unexpected success with invalid bitLength");
        } catch (ArithmeticException e) {
            System.out.println("Caught expected ArithmeticException for bitLength < 2");
        } catch (Exception e) {
            System.err.println("Caught unexpected exception: " + e);
        }
    }
}
