/* @test    @summary Test for BigInteger.probablePrime(int, Random) with valid and invalid inputs    @run main BigIntegerWitupTest */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testProbablePrimeValid();
        testProbablePrimeInvalid();
    }

    private static void testProbablePrimeValid() {
        try {
            int[] validBitLengths = {2, 16, 1024};
            Random rnd = new Random(12345);
            for (int bitLength : validBitLengths) {
                BigInteger result = BigInteger.probablePrime(bitLength, rnd);
                if (result == null) {
                    throw new AssertionError("Expected non-null result for bitLength=" + bitLength);
                }
                // Verify that the bitLength of the result is at least the requested bitLength
                if (result.bitLength() < bitLength) {
                    throw new AssertionError("Result bitLength " + result.bitLength() + " is less than requested " + bitLength);
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid input test: " + e, e);
        }
    }

    private static void testProbablePrimeInvalid() {
        try {
            int invalidBitLength = 1;
            Random rnd = new Random(54321);
            BigInteger.probablePrime(invalidBitLength, rnd);
            throw new AssertionError("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!e.getMessage().contains("bitLength < 2")) {
                throw new AssertionError("Unexpected message in ArithmeticException: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
