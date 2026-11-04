/*
 * @test
 * @summary Tests BigInteger.mod for positive, negative, zero and invalid modulus inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.valueOf(10).mod(BigInteger.valueOf(3)).equals(BigInteger.valueOf(1)), "10 mod 3 should be 1");
        check(BigInteger.valueOf(-10).mod(BigInteger.valueOf(3)).equals(BigInteger.valueOf(2)), "-10 mod 3 should be 2");
        check(BigInteger.valueOf(-1).mod(BigInteger.valueOf(5)).equals(BigInteger.valueOf(4)), "-1 mod 5 should be 4");
        check(BigInteger.valueOf(0).mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO), "0 mod 5 should be 0");
        check(BigInteger.valueOf(-9).mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO), "-9 mod 3 should be 0");
        check(BigInteger.valueOf(7).mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO), "7 mod 7 should be 0");
        check(BigInteger.valueOf(-7).mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO), "-7 mod 7 should be 0");
        check(BigInteger.valueOf(2).mod(BigInteger.valueOf(10)).equals(BigInteger.valueOf(2)), "2 mod 10 should be 2");

        expectArithmetic(BigInteger.valueOf(5), BigInteger.ZERO);
        expectArithmetic(BigInteger.valueOf(5), BigInteger.valueOf(-3));
        expectArithmetic(BigInteger.valueOf(-5), BigInteger.valueOf(-1));

        BigInteger x = new BigInteger("-123456789012345678901234567890");
        BigInteger m = BigInteger.valueOf(97);
        BigInteger r = x.mod(m);
        check(r.signum() >= 0, "large negative mod must be non-negative");
        check(r.compareTo(m) < 0, "large negative mod must be less than modulus");
        check(x.subtract(r).remainder(m).signum() == 0, "x - (x mod m) must be divisible by m");

        BigInteger y = new BigInteger("987654321098765432109876543210");
        BigInteger m2 = new BigInteger("1000000007");
        BigInteger r2 = y.mod(m2);
        check(r2.signum() >= 0 && r2.compareTo(m2) < 0, "large positive mod must be in [0, m)");
        check(y.subtract(r2).remainder(m2).signum() == 0, "y - (y mod m) must be divisible by m");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectArithmetic(BigInteger value, BigInteger modulus) {
        try {
            value.mod(modulus);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("expected ArithmeticException for mod with modulus " + modulus);
    }
}
