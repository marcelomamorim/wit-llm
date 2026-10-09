/* @test    @summary Test probablePrime with valid bitLength and Random    @run main BigIntegerWitupTest_P7279352076f4 */
/* @modules java.base */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest_P7279352076f4 {
    public static void main(String[] args) {
        testProbablePrimeSmallBitLength();
        testProbablePrimeLargeBitLength();
        testProbablePrimeInvalidBitLength();
    }

    private static void testProbablePrimeSmallBitLength() {
        int bitLength = 2;
        Random rnd = new Random(12345);
        BigInteger result = BigInteger.probablePrime(bitLength, rnd);
        assert result != null : "Result should not be null for small bitLength";
        assert result.bitLength() >= bitLength - 1 : "Result bit length is less than expected";
        System.out.println("testProbablePrimeSmallBitLength passed");
    }

    private static void testProbablePrimeLargeBitLength() {
        int bitLength = 1024;
        Random rnd = new Random(54321);
        BigInteger result = BigInteger.probablePrime(bitLength, rnd);
        assert result != null : "Result should not be null for large bitLength";
        assert result.bitLength() >= bitLength - 1 : "Result bit length is less than expected";
        System.out.println("testProbablePrimeLargeBitLength passed");
    }

    private static void testProbablePrimeInvalidBitLength() {
        int[] invalidBitLengths = {0, 1};
        for (int bitLength : invalidBitLengths) {
            try {
                Random rnd = new Random();
                BigInteger.probablePrime(bitLength, rnd);
                assert false : "Expected ArithmeticException for bitLength=" + bitLength;
            } catch (ArithmeticException e) {
                assert e.getMessage().contains("bitLength < 2") : "Unexpected exception message";
            }
        }
        System.out.println("testProbablePrimeInvalidBitLength passed");
    }
}
