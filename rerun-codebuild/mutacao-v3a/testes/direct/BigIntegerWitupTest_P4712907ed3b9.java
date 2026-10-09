/* @test    @summary Test the mod() method of BigInteger for positive modulus and negative remainders    @run main BigIntegerWitupTest_P4712907ed3b9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P4712907ed3b9 {

    public static void main(String[] args) {
        testModWithPositiveRemainder();
        testModWithNegativeRemainder();
        testModWithZeroRemainder();
        testModWithNegativeModulus();
    }

    private static void testModWithPositiveRemainder() {
        BigInteger a = new BigInteger("20");
        BigInteger m = new BigInteger("6");
        BigInteger result = a.mod(m);
        if (!result.equals(BigInteger.valueOf(2))) {
            throw new AssertionError("Expected 2, got: " + result);
        }
    }

    private static void testModWithNegativeRemainder() {
        BigInteger a = new BigInteger("-20");
        BigInteger m = new BigInteger("6");
        BigInteger result = a.mod(m);
        if (!result.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Expected 4, got: " + result);
        }
    }

    private static void testModWithZeroRemainder() {
        BigInteger a = new BigInteger("24");
        BigInteger m = new BigInteger("6");
        BigInteger result = a.mod(m);
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected 0, got: " + result);
        }
    }

    private static void testModWithNegativeModulus() {
        BigInteger a = new BigInteger("10");
        BigInteger m = new BigInteger("-5");
        boolean exceptionThrown = false;
        try {
            a.mod(m);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        }
    }
}
