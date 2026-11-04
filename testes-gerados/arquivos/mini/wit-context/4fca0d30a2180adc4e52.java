/*
 * @test
 * @summary Test constructor of ECPoint for null checks and valid construction
 * @run main ECPointWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidCoordinates();
        testConstructorWithNullX();
        testConstructorWithNullY();
    }

    private static void testConstructorWithValidCoordinates() {
        BigInteger x = BigInteger.valueOf(123456789L);
        BigInteger y = BigInteger.valueOf(987654321L);
        ECPoint point = new ECPoint(x, y);
        if (!x.equals(point.getAffineX())) {
            throw new RuntimeException("getAffineX() did not return expected value");
        }
        if (!y.equals(point.getAffineY())) {
            throw new RuntimeException("getAffineY() did not return expected value");
        }
    }

    private static void testConstructorWithNullX() {
        BigInteger y = BigInteger.ONE;
        try {
            new ECPoint(null, y);
            throw new RuntimeException("Expected NullPointerException for null x coordinate");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testConstructorWithNullY() {
        BigInteger x = BigInteger.ONE;
        try {
            new ECPoint(x, null);
            throw new RuntimeException("Expected NullPointerException for null y coordinate");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
