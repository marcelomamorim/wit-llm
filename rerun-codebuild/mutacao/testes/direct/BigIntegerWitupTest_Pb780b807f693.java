/* @test    @summary Test BigInteger.mod() with positive modulus and positive, negative, zero, and invalid modulus values    @run main BigIntegerWitupTest_Pb780b807f693 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pb780b807f693 {

    public static void main(String[] args) {
        testModWithPositiveModulus();
        testModWithNegativeModulus();
        testModWithZeroModulus();
        testModWithNegativeResult();
    }

    private static void testModWithPositiveModulus() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = new BigInteger("10000");
        BigInteger result = a.mod(m);
        BigInteger expected = a.remainder(m).signum() >= 0 ? a.remainder(m) : a.remainder(m).add(m);
        assert result.equals(expected) : "Failed: mod with positive modulus";
    }

    private static void testModWithNegativeModulus() {
        BigInteger a = new BigInteger("987654321");
        BigInteger m = new BigInteger("-10000");
        try {
            a.mod(m);
            assert false : "Expected ArithmeticException for negative modulus";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("BigInteger: modulus not positive") : "Unexpected exception message";
        }
    }

    private static void testModWithZeroModulus() {
        BigInteger a = new BigInteger("12345");
        BigInteger m = BigInteger.ZERO;
        try {
            a.mod(m);
            assert false : "Expected ArithmeticException for zero modulus";
        } catch (ArithmeticException e) {
            assert e.getMessage().equals("BigInteger: modulus not positive") : "Unexpected exception message";
        }
    }

    private static void testModWithNegativeResult() {
        BigInteger a = new BigInteger("-12345");
        BigInteger m = new BigInteger("10000");
        BigInteger result = a.mod(m);
        BigInteger expected = a.remainder(m).signum() >= 0 ? a.remainder(m) : a.remainder(m).add(m);
        assert result.equals(expected) : "Failed: mod with negative dividend";
    }
}
