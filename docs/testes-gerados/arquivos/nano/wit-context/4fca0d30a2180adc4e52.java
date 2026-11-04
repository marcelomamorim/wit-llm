/* @test    @summary Test for ECPoint constructor with null parameters to verify NullPointerException.    @run main ECPointWitupTest */

import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullX();
        testConstructorWithNullY();
        testConstructorWithValidCoordinates();
    }

    private static void testConstructorWithNullX() {
        try {
            new ECPoint(null, BigInteger.ONE);
            System.err.println("Failed: Expected NullPointerException for null x");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null x");
        }
    }

    private static void testConstructorWithNullY() {
        try {
            new ECPoint(BigInteger.ONE, null);
            System.err.println("Failed: Expected NullPointerException for null y");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null y");
        }
    }

    private static void testConstructorWithValidCoordinates() {
        BigInteger x = BigInteger.valueOf(12345);
        BigInteger y = BigInteger.valueOf(67890);
        ECPoint point = new ECPoint(x, y);
        if (point.getAffineX().equals(x) && point.getAffineY().equals(y)) {
            System.out.println("Passed: Valid coordinates are correctly assigned");
        } else {
            System.err.println("Failed: Coordinates do not match");
        }
    }
}
