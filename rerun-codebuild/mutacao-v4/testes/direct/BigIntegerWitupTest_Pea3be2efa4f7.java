/* @test
   @summary Test the modInverse method of BigInteger for positive modulus.
   @run main BigIntegerWitupTest_Pea3be2efa4f7
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pea3be2efa4f7 {
    public static void main(String[] args) {
        // Test case: modular inverse of 3 mod 11
        BigInteger a = BigInteger.valueOf(3);
        BigInteger m = BigInteger.valueOf(11);
        BigInteger inv = a.modInverse(m);
        // Verify that (a * inv) mod m == 1
        BigInteger product = a.multiply(inv).mod(m);
        if (!product.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: 3^-1 mod 11 != 1, got " + product);
        }
        // Test case: modular inverse of 10 mod 17
        BigInteger a2 = BigInteger.valueOf(10);
        BigInteger m2 = BigInteger.valueOf(17);
        BigInteger inv2 = a2.modInverse(m2);
        BigInteger product2 = a2.multiply(inv2).mod(m2);
        if (!product2.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: 10^-1 mod 17 != 1, got " + product2);
        }
        // Test case: modular inverse of 1 mod any positive number
        BigInteger a3 = BigInteger.ONE;
        BigInteger m3 = BigInteger.valueOf(13);
        BigInteger inv3 = a3.modInverse(m3);
        if (!inv3.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: 1^-1 mod 13 != 1, got " + inv3);
        }
        // Test case: negative number should produce correct inverse
        BigInteger a4 = BigInteger.valueOf(-3);
        BigInteger m4 = BigInteger.valueOf(13);
        BigInteger inv4 = a4.modInverse(m4);
        BigInteger product4 = a4.multiply(inv4).mod(m4);
        if (!product4.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: -3^-1 mod 13 != 1, got " + product4);
        }
        // Test case: modulus not positive should throw ArithmeticException
        try {
            BigInteger a5 = BigInteger.valueOf(5);
            BigInteger m5 = BigInteger.valueOf(0);
            a5.modInverse(m5);
            throw new AssertionError("Expected ArithmeticException for modulus 0");
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            BigInteger a6 = BigInteger.valueOf(5);
            BigInteger m6 = BigInteger.valueOf(-7);
            a6.modInverse(m6);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
