/* @test    @summary Test probablePrime method for valid and invalid bit lengths    @run main BigIntegerWitupTest_P0d1822e95141 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest_P0d1822e95141 {

    public static void main(String[] args) {
        testProbablePrimeWithValidBitLength();
        testProbablePrimeWithInvalidBitLength();
    }

    static void testProbablePrimeWithValidBitLength() {
        Random rnd = new Random(12345);
        int[] validBitLengths = {2, 16, 32, 64, 128};
        for (int bitLength : validBitLengths) {
            BigInteger primeCandidate = BigInteger.probablePrime(bitLength, rnd);
            assert primeCandidate != null : "probablePrime returned null for bitLength=" + bitLength;
            // Verify that the bit length is as expected or greater
            int actualBitLength = primeCandidate.bitLength();
            assert actualBitLength >= bitLength - 1 : "Generated prime has smaller bit length: " + actualBitLength + " for requested: " + bitLength;
        }
    }

    static void testProbablePrimeWithInvalidBitLength() {
        Random rnd = new Random(54321);
        int[] invalidBitLengths = {0, 1};
        for (int bitLength : invalidBitLengths) {
            try {
                BigInteger.probablePrime(bitLength, rnd);
                assert false : "Expected ArithmeticException for bitLength=" + bitLength;
            } catch (ArithmeticException e) {
                assert e.getMessage().contains("bitLength < 2") : "Unexpected exception message: " + e.getMessage();
            }
        }
    }
}
