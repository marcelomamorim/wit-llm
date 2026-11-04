/*
 * @test
 * @summary Verifies BigInteger.sqrt() returns the floor of the square root and rejects negative values
 * @run main BigIntegerWitupTest
 */

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(BigInteger.ZERO.sqrt(), BigInteger.ZERO, "sqrt(0)");
        check(BigInteger.ONE.sqrt(), BigInteger.ONE, "sqrt(1)");
        check(BigInteger.valueOf(15).sqrt(), BigInteger.valueOf(3), "sqrt(15)");
        check(BigInteger.valueOf(16).sqrt(), BigInteger.valueOf(4), "sqrt(16)");
        check(BigInteger.valueOf(17).sqrt(), BigInteger.valueOf(4), "sqrt(17)");
        check(new BigInteger("100000000000000000000").sqrt(),
              new BigInteger("10000000000"), "sqrt(1e20)");

        BigInteger one = BigInteger.ONE;
        check(one.shiftLeft(100).subtract(one).sqrt(),
              one.shiftLeft(50).subtract(one), "sqrt(2^100-1)");
        check(one.shiftLeft(100).sqrt(), one.shiftLeft(50), "sqrt(2^100)");

        boolean thrown = false;
        try {
            BigInteger.valueOf(-1).sqrt();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("sqrt() of negative value did not throw ArithmeticException");
        }
    }

    private static void check(BigInteger actual, BigInteger expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }
}
