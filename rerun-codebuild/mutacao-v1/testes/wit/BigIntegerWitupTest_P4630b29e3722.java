/* @test    @summary Test BigInteger.probablePrime for valid and invalid inputs    @run main BigIntegerWitupTest_P4630b29e3722 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest_P4630b29e3722 {

    public static void main(String[] args) {
        testProbablePrimeValid();
        testProbablePrimeInvalid();
    }

    static void testProbablePrimeValid() {
        try {
            // Valid bitLength: 2
            BigInteger prime = BigInteger.probablePrime(2, new Random());
            if (prime == null) {
                throw new AssertionError("Expected non-null BigInteger for valid bitLength");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid bitLength: " + e);
        }
        try {
            // Valid bitLength: 10
            BigInteger prime = BigInteger.probablePrime(10, new Random());
            if (prime == null) {
                throw new AssertionError("Expected non-null BigInteger for valid bitLength");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid bitLength: " + e);
        }
    }

    static void testProbablePrimeInvalid() {
        try {
            // Invalid bitLength: 1 (should throw ArithmeticException)
            BigInteger.probablePrime(1, new Random());
            throw new AssertionError("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            // Invalid bitLength: 0
            BigInteger.probablePrime(0, new Random());
            throw new AssertionError("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            // Invalid bitLength: -5
            BigInteger.probablePrime(-5, new Random());
            throw new AssertionError("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
