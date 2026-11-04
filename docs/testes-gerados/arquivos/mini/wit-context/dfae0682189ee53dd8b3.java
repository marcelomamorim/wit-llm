/*
 * @test
 * @summary Test BigInteger.mod(BigInteger) for correct behavior and exception on non-positive modulus
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testModWithPositiveModulus();
        testModWithZeroModulus();
        testModWithNegativeModulus();
    }

    private static void testModWithPositiveModulus() {
        BigInteger val = new BigInteger("123456789");
        BigInteger mod = new BigInteger("10000");
        BigInteger result = val.mod(mod);
        // result should be non-negative and less than mod
        if (result.signum() < 0) {
            throw new AssertionError("Result of mod is negative");
        }
        if (result.compareTo(mod) >= 0) {
            throw new AssertionError("Result of mod is not less than modulus");
        }
    }

    private static void testModWithZeroModulus() {
        BigInteger val = new BigInteger("123456789");
        BigInteger zeroMod = BigInteger.ZERO;
        try {
            val.mod(zeroMod);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("modulus not positive")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testModWithNegativeModulus() {
        BigInteger val = new BigInteger("123456789");
        BigInteger negMod = new BigInteger("-1");
        try {
            val.mod(negMod);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("modulus not positive")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
