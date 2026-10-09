/* @test    @summary Test for BigInteger.nextProbablePrime() handling negative values    @run main BigIntegerWitupTest_P50a9517b8e20 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P50a9517b8e20 {
    public static void main(String[] args) {
        testNegativeInput();
        testZeroInput();
        testOneInput();
        testSmallNumber();
        testLargeNumber();
    }

    static void testNegativeInput() {
        BigInteger negative = BigInteger.valueOf(-1);
        try {
            negative.nextProbablePrime();
            System.out.println("FAIL: Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Caught expected ArithmeticException for negative input");
        }
    }

    static void testZeroInput() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.nextProbablePrime();
        if (result.equals(BigInteger.TWO)) {
            System.out.println("PASS: Zero input returns 2");
        } else {
            System.out.println("FAIL: Zero input did not return 2, got " + result);
        }
    }

    static void testOneInput() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.nextProbablePrime();
        if (result.equals(BigInteger.TWO)) {
            System.out.println("PASS: One input returns 2");
        } else {
            System.out.println("FAIL: One input did not return 2, got " + result);
        }
    }

    static void testSmallNumber() {
        BigInteger small = BigInteger.valueOf(17);
        BigInteger prime = small.nextProbablePrime();
        if (prime.compareTo(small) > 0 && prime.isProbablePrime(10)) {
            System.out.println("PASS: Small number next probable prime is " + prime);
        } else {
            System.out.println("FAIL: Small number next probable prime test failed");
        }
    }

    static void testLargeNumber() {
        BigInteger large = BigInteger.valueOf(10).pow(50);
        BigInteger prime = large.nextProbablePrime();
        if (prime.compareTo(large) > 0 && prime.isProbablePrime(10)) {
            System.out.println("PASS: Large number next probable prime is " + prime);
        } else {
            System.out.println("FAIL: Large number next probable prime test failed");
        }
    }
}
