/* @test    @summary Test for BigInteger.nextProbablePrime() method to verify exception handling and behavior.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testNegativeBigInteger();
        testZeroBigInteger();
        testOneBigInteger();
        testLargeNumberPrime();
    }

    private static void testNegativeBigInteger() {
        try {
            BigInteger negative = new BigInteger("-5");
            negative.nextProbablePrime();
            System.out.println("FAIL: Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for negative input");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testZeroBigInteger() {
        try {
            BigInteger zero = BigInteger.ZERO;
            zero.nextProbablePrime();
            System.out.println("PASS: No exception for zero, behavior as per implementation");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero: " + e);
        }
    }

    private static void testOneBigInteger() {
        try {
            BigInteger one = BigInteger.ONE;
            one.nextProbablePrime();
            System.out.println("PASS: No exception for one, behavior as per implementation");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for one: " + e);
        }
    }

    private static void testLargeNumberPrime() {
        try {
            // Using a known large prime number
            String largePrimeStr = "32416190071"; // 11-digit prime
            BigInteger largePrime = new BigInteger(largePrimeStr);
            BigInteger result = largePrime.nextProbablePrime();
            if (result != null && result.isProbablePrime(100)) {
                System.out.println("PASS: Next probable prime for large number is prime");
            } else {
                System.out.println("FAIL: Result is not prime or null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during large number prime test: " + e);
        }
    }
}
