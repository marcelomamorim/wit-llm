/*
 * @test
 * @summary Test constructor of ECPoint with valid and null arguments
 * @run main ECPointWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullXThrows();
        testNullYThrows();
    }

    private static void testValidConstruction() {
        BigInteger x = BigInteger.valueOf(12345L);
        BigInteger y = BigInteger.valueOf(67890L);
        ECPoint point = new ECPoint(x, y);
        if (!x.equals(point.getAffineX())) {
            throw new RuntimeException("getAffineX() returned unexpected value");
        }
        if (!y.equals(point.getAffineY())) {
            throw new RuntimeException("getAffineY() returned unexpected value");
        }
    }

    private static void testNullXThrows() {
        BigInteger y = BigInteger.ONE;
        try {
            new ECPoint(null, y);
            throw new RuntimeException("Expected NullPointerException for null x");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("affine coordinate x or y is null")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testNullYThrows() {
        BigInteger x = BigInteger.ONE;
        try {
            new ECPoint(x, null);
            throw new RuntimeException("Expected NullPointerException for null y");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("affine coordinate x or y is null")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
