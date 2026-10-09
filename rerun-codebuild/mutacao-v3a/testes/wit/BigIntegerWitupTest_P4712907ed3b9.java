/* @test    @summary Test for BigInteger.mod with valid and invalid inputs    @run main BigIntegerWitupTest_P4712907ed3b9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P4712907ed3b9 {

    public static void main(String[] args) {
        testModWithPositiveDivisor();
        testModWithZeroDivisor();
        testModWithNegativeDivisor();
    }

    private static void testModWithPositiveDivisor() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = new BigInteger("10000");
        BigInteger result = a.mod(m);
        System.out.println("testModWithPositiveDivisor: " + result);
        // Esperado: valor positivo, 6789
        if (!result.equals(new BigInteger("6789"))) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    private static void testModWithZeroDivisor() {
        BigInteger a = new BigInteger("12345");
        BigInteger m = BigInteger.ZERO;
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for zero divisor");
        } catch (ArithmeticException e) {
            // esperado
            System.out.println("testModWithZeroDivisor: Caught expected exception: " + e.getMessage());
        }
    }

    private static void testModWithNegativeDivisor() {
        BigInteger a = new BigInteger("98765");
        BigInteger m = new BigInteger("-1000");
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for negative divisor");
        } catch (ArithmeticException e) {
            // esperado
            System.out.println("testModWithNegativeDivisor: Caught expected exception: " + e.getMessage());
        }
    }
}
