/* @test
   @summary Verifica comportamento do método BigInteger.mod, incluindo exceção para modulus não positivo.
   @run main BigIntegerWitupTest_P1f62ac237497
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P1f62ac237497 {
    public static void main(String[] args) {
        testModWithPositiveModulus();
        testModWithZeroModulus();
        testModWithNegativeModulus();
    }

    private static void testModWithPositiveModulus() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = new BigInteger("10000");
        BigInteger result = a.mod(m);
        BigInteger expected = a.remainder(m).signum() >= 0 ? a.remainder(m) : a.remainder(m).add(m);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: Expected " + expected + " but got " + result);
        }
    }

    private static void testModWithZeroModulus() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = BigInteger.ZERO;
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testModWithNegativeModulus() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = new BigInteger("-10000");
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
