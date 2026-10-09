/* @test
   @summary Test the nextProbablePrime() method of BigInteger.
   @run main BigIntegerWitupTest_Pd41fda4e7686
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pd41fda4e7686 {
    public static void main(String[] args) {
        testNegativeNumber();
        testZero();
        testOne();
        testSmallNumber();
        testLargeNumber();
    }

    static void testNegativeNumber() {
        BigInteger negative = BigInteger.valueOf(-5);
        try {
            negative.nextProbablePrime();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testZero() {
        BigInteger zero = BigInteger.ZERO;
        try {
            zero.nextProbablePrime();
            throw new AssertionError("Expected ArithmeticException for zero input");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testOne() {
        BigInteger one = BigInteger.ONE;
        BigInteger result = one.nextProbablePrime();
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.compareTo(BigInteger.TWO) < 0) {
            throw new AssertionError("Prime should be >= 2");
        }
        if (!result.isProbablePrime(10)) {
            throw new AssertionError("Result is not prime");
        }
    }

    static void testSmallNumber() {
        BigInteger small = BigInteger.valueOf(14);
        BigInteger prime = small.nextProbablePrime();
        if (prime == null) {
            throw new AssertionError("Prime should not be null");
        }
        if (prime.compareTo(small) <= 0) {
            throw new AssertionError("Prime should be greater than input");
        }
        if (!prime.isProbablePrime(10)) {
            throw new AssertionError("Result is not prime");
        }
    }

    static void testLargeNumber() {
        BigInteger large = BigInteger.valueOf(1L << 60);
        BigInteger prime = large.nextProbablePrime();
        if (prime == null) {
            throw new AssertionError("Prime should not be null");
        }
        if (prime.compareTo(large) <= 0) {
            throw new AssertionError("Prime should be greater than input");
        }
        if (!prime.isProbablePrime(10)) {
            throw new AssertionError("Result is not prime");
        }
    }
}
