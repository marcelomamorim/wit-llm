/* @test    @summary Test for BigInteger.modInverse(BigInteger m) exception path when m.signum != 1    @run main BigIntegerWitupTest_P9b4050a9eec6 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P9b4050a9eec6 {
    public static void main(String[] args) {
        testModInverseThrowsOnNonPositiveModulus();
    }

    private static void testModInverseThrowsOnNonPositiveModulus() {
        BigInteger[] testValues = {
            BigInteger.valueOf(10), // positive
            BigInteger.ZERO, // zero
            BigInteger.valueOf(-10) // negative
        };
        for (BigInteger m : testValues) {
            if (m.signum() != 1) {
                try {
                    BigInteger bi = BigInteger.valueOf(123);
                    bi.modInverse(m);
                    throw new AssertionError("Expected ArithmeticException for m.signum() != 1 with m = " + m);
                } catch (ArithmeticException e) {
                    if (!e.getMessage().contains("modulus not positive")) {
                        throw new AssertionError("Unexpected exception message: " + e.getMessage());
                    }
                }
            }
        }
    }
}
