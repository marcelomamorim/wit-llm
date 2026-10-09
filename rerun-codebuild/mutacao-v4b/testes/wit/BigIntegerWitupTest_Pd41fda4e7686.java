/* @test
   @summary Testa o método nextProbablePrime() de BigInteger para comportamentos observáveis.
   @run main BigIntegerWitupTest_Pd41fda4e7686
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pd41fda4e7686 {
    public static void main(String[] args) {
        testNegativeBigInteger();
        testZeroBigInteger();
        testOneBigInteger();
        testSmallNumberPrime();
        testSmallNumberComposite();
        testLargeNumberPrime();
        testLargeNumberComposite();
    }

    static void testNegativeBigInteger() {
        try {
            BigInteger bi = new BigInteger("-5");
            bi.nextProbablePrime();
            throw new AssertionError("Expected ArithmeticException for negative signum");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testZeroBigInteger() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.TWO)) {
            throw new AssertionError("Expected 2 for zero input, got: " + result);
        }
    }

    static void testOneBigInteger() {
        BigInteger bi = BigInteger.ONE;
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.TWO)) {
            throw new AssertionError("Expected 2 for one input, got: " + result);
        }
    }

    static void testSmallNumberPrime() {
        BigInteger bi = new BigInteger("13");
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.valueOf(13))) {
            throw new AssertionError("Expected 13 as it is already prime, got: " + result);
        }
    }

    static void testSmallNumberComposite() {
        BigInteger bi = new BigInteger("15");
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.valueOf(17))) {
            throw new AssertionError("Expected 17 after 15, got: " + result);
        }
    }

    static void testLargeNumberPrime() {
        // Number just below a known prime
        BigInteger bi = new BigInteger("9999999967");
        BigInteger result = bi.nextProbablePrime();
        if (result.compareTo(bi) <= 0) {
            throw new AssertionError("Result should be greater than input");
        }
        if (!result.primeToCertainty(10, null)) {
            throw new AssertionError("Result should be prime");
        }
    }

    static void testLargeNumberComposite() {
        // Number that is not prime
        BigInteger bi = new BigInteger("9999999966");
        BigInteger result = bi.nextProbablePrime();
        if (result.compareTo(bi) <= 0) {
            throw new AssertionError("Result should be greater than input");
        }
        if (!result.primeToCertainty(10, null)) {
            throw new AssertionError("Result should be prime");
        }
    }
}
