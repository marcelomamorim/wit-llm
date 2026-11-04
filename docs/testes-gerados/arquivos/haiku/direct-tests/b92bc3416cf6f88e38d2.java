/*
 * @test
 * @summary Checks BigInteger.sqrtAndRemainder() invariants: s*s + r == n and 0 <= r <= 2s
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void verify(BigInteger n, BigInteger expectedS, BigInteger expectedR) {
        BigInteger[] res = n.sqrtAndRemainder();
        check(res != null && res.length == 2, "result array must have length 2 for " + n);
        BigInteger s = res[0];
        BigInteger r = res[1];
        check(s.equals(expectedS), "sqrt mismatch for " + n + ": " + s + " expected " + expectedS);
        check(r.equals(expectedR), "remainder mismatch for " + n + ": " + r + " expected " + expectedR);
        check(s.multiply(s).add(r).equals(n), "s*s + r != n for " + n);
        check(r.signum() >= 0, "remainder negative for " + n);
        check(r.compareTo(s.shiftLeft(1)) <= 0, "remainder exceeds 2s for " + n);
    }

    public static void main(String[] args) {
        verify(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);
        verify(BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
        verify(BigInteger.valueOf(2), BigInteger.ONE, BigInteger.ONE);
        verify(BigInteger.valueOf(15), BigInteger.valueOf(3), BigInteger.valueOf(6));
        verify(BigInteger.valueOf(16), BigInteger.valueOf(4), BigInteger.ZERO);
        verify(BigInteger.valueOf(17), BigInteger.valueOf(4), BigInteger.ONE);
        verify(BigInteger.valueOf(24), BigInteger.valueOf(4), BigInteger.valueOf(8));
        verify(BigInteger.valueOf(25), BigInteger.valueOf(5), BigInteger.ZERO);

        BigInteger ten10 = BigInteger.TEN.pow(10);
        BigInteger ten20 = BigInteger.TEN.pow(20);
        verify(ten20, ten10, BigInteger.ZERO);
        verify(ten20.add(BigInteger.valueOf(5)), ten10, BigInteger.valueOf(5));
        verify(ten20.subtract(BigInteger.ONE), ten10.subtract(BigInteger.ONE),
               ten10.shiftLeft(1).subtract(BigInteger.ONE));

        BigInteger big = new BigInteger("123456789012345678901234567890");
        BigInteger[] res = big.sqrtAndRemainder();
        BigInteger s = res[0];
        BigInteger r = res[1];
        check(s.multiply(s).add(r).equals(big), "invariant failed for large value");
        check(r.compareTo(s.shiftLeft(1)) <= 0, "remainder bound failed for large value");
        check(s.add(BigInteger.ONE).multiply(s.add(BigInteger.ONE)).compareTo(big) > 0,
              "sqrt is not floor for large value");

        System.out.println("PASSED");
    }
}
