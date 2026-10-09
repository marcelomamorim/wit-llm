/* @test
   @summary Test probablePrime method for valid and invalid bit lengths.
   @run main BigIntegerWitupTest_P5b78730dce4a
 */
import java.util.Random;
import java.math.BigInteger;
public class BigIntegerWitupTest_P5b78730dce4a {
    public static void main(String[] args) {
        testProbablePrimeValid();
        testProbablePrimeInvalid();
    }
    private static void testProbablePrimeValid() {
        int[] validBitLengths = {2, 16, 32, 64, 128};
        Random rnd = new Random(12345);
        for (int bits : validBitLengths) {
            BigInteger prime = BigInteger.probablePrime(bits, rnd);
            assert prime.bitLength() >= bits - 1 && prime.bitLength() <= bits + 1 : "Prime bit length mismatch for bits=" + bits;
        }
    }
    private static void testProbablePrimeInvalid() {
        int[] invalidBitLengths = {0, 1};
        for (int bits : invalidBitLengths) {
            try {
                BigInteger.probablePrime(bits, new Random());
                throw new AssertionError("Expected ArithmeticException for bitLength=" + bits);
            } catch (ArithmeticException e) {
                // expected
            }
        }
    }
}
