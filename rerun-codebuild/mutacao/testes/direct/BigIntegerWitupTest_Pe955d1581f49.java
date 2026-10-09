/* @test    @summary Test nextProbablePrime() method of BigInteger for positive inputs and edge cases    @run main BigIntegerWitupTest_Pe955d1581f49 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pe955d1581f49 {

    public static void main(String[] args) {
        testNextProbablePrimeZero();
        testNextProbablePrimeOne();
        testNextProbablePrimeNegative();
        testNextProbablePrimeSmallNumber();
        testNextProbablePrimeLargeNumber();
    }

    static void testNextProbablePrimeZero() {
        BigInteger zero = BigInteger.ZERO;
        try {
            zero.nextProbablePrime();
        } catch (ArithmeticException e) {
            System.out.println("Passed: nextProbablePrime() throws ArithmeticException for zero.");
            return;
        }
        System.out.println("Failed: nextProbablePrime() should throw ArithmeticException for zero.");
    }

    static void testNextProbablePrimeOne() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.nextProbablePrime();
        if (result.equals(BigInteger.TWO)) {
            System.out.println("Passed: nextProbablePrime() of 1 returns 2.");
        } else {
            System.out.println("Failed: nextProbablePrime() of 1 should return 2, got " + result);
        }
    }

    static void testNextProbablePrimeNegative() {
        BigInteger negative = BigInteger.valueOf(-5);
        try {
            negative.nextProbablePrime();
        } catch (ArithmeticException e) {
            System.out.println("Passed: nextProbablePrime() throws ArithmeticException for negative number.");
            return;
        }
        System.out.println("Failed: nextProbablePrime() should throw ArithmeticException for negative number.");
    }

    static void testNextProbablePrimeSmallNumber() {
        BigInteger smallNumber = BigInteger.valueOf(17);
        BigInteger prime = smallNumber.nextProbablePrime();
        if (prime.isProbablePrime(10)) {
            System.out.println("Passed: nextProbablePrime() of 17 returns a probable prime: " + prime);
        } else {
            System.out.println("Failed: nextProbablePrime() of 17 did not return a probable prime: " + prime);
        }
    }

    static void testNextProbablePrimeLargeNumber() {
        BigInteger largeNumber = new BigInteger("9999999967");
        BigInteger prime = largeNumber.nextProbablePrime();
        if (prime.isProbablePrime(10)) {
            System.out.println("Passed: nextProbablePrime() of large number returns a probable prime: " + prime);
        } else {
            System.out.println("Failed: nextProbablePrime() of large number did not return a probable prime: " + prime);
        }
    }
}
