/* @test
   @summary Verifica comportamento do método modInverse de BigInteger.
   @run main BigIntegerWitupTest_Pea3be2efa4f7
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pea3be2efa4f7 {
    public static void main(String[] args) {
        testModInverseWithPositiveMod();
        testModInverseWithZeroMod();
        testModInverseWithNegativeMod();
        testModInverseWithOneMod();
    }

    private static void testModInverseWithPositiveMod() {
        BigInteger a = BigInteger.valueOf(42);
        BigInteger m = BigInteger.valueOf(13);
        try {
            BigInteger inv = a.modInverse(m);
            // Verifica se a inversa multiplicativa existe e é válida
            BigInteger prod = a.multiply(inv).mod(m);
            if (!prod.equals(BigInteger.ONE)) {
                throw new AssertionError("Failed: a * inv mod m != 1");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected exception for positive modulus", e);
        }
    }

    private static void testModInverseWithZeroMod() {
        BigInteger a = BigInteger.valueOf(42);
        BigInteger m = BigInteger.ZERO;
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            // esperado
            if (!e.getMessage().contains("modulus not positive")) {
                throw new AssertionError("Unexpected message", e);
            }
        }
    }

    private static void testModInverseWithNegativeMod() {
        BigInteger a = BigInteger.valueOf(42);
        BigInteger m = BigInteger.valueOf(-13);
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // esperado
            if (!e.getMessage().contains("modulus not positive")) {
                throw new AssertionError("Unexpected message", e);
            }
        }
    }

    private static void testModInverseWithOneMod() {
        BigInteger a = BigInteger.valueOf(42);
        BigInteger m = BigInteger.ONE;
        try {
            BigInteger inv = a.modInverse(m);
            if (!inv.equals(BigInteger.ZERO)) {
                throw new AssertionError("Inverse mod 1 should be 0");
            }
        } catch (ArithmeticException e) {
            throw new AssertionError("Unexpected exception for modulus 1", e);
        }
    }
}
