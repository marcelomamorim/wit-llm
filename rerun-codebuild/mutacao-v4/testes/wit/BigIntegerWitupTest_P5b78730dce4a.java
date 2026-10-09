/* @test
   @summary Tests probablePrime method for edge cases and normal behavior.
   @run main BigIntegerWitupTest_P5b78730dce4a
 */
import java.util.Random;
import java.math.BigInteger;
public class BigIntegerWitupTest_P5b78730dce4a {
    public static void main(String[] args) {
        testProbablePrimeWithInvalidBitLength();
        testProbablePrimeWithSmallBitLength();
        testProbablePrimeWithLargeBitLength();
    }
    private static void testProbablePrimeWithInvalidBitLength() {
        try {
            BigInteger.probablePrime(1, new Random());
            throw new AssertionError("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            // expected
        }
    }
    private static void testProbablePrimeWithSmallBitLength() {
        int bitLength = 2;
        Random rnd = new Random(12345);
        BigInteger result = BigInteger.probablePrime(bitLength, rnd);
        if (result.bitLength() != bitLength) {
            throw new AssertionError("Result bitLength mismatch");
        }
        if (result.signum() <= 0) {
            throw new AssertionError("Result should be positive");
        }
    }
    private static void testProbablePrimeWithLargeBitLength() {
        int bitLength = 512;
        Random rnd = new Random(54321);
        BigInteger result = BigInteger.probablePrime(bitLength, rnd);
        if (result.bitLength() != bitLength) {
            throw new AssertionError("Result bitLength mismatch");
        }
        if (result.signum() <= 0) {
            throw new AssertionError("Result should be positive");
        }
    }
}
