/* @test    @summary Test ECPoint constructor with valid BigInteger coordinates    @run main ECPointWitupTest */

import java.math.BigInteger;
import java.security.spec.ECPoint;

public class ECPointWitupTest {

    public static void main(String[] args) {
        testValidCoordinates();
        testNullXCoordinate();
        testNullYCoordinate();
        testPointInfinity();
    }

    static void testValidCoordinates() {
        BigInteger x = BigInteger.valueOf(12345);
        BigInteger y = BigInteger.valueOf(67890);
        ECPoint point = new ECPoint(x, y);
        assert point.getAffineX().equals(x) : "Affine X coordinate mismatch";
        assert point.getAffineY().equals(y) : "Affine Y coordinate mismatch";
    }

    static void testNullXCoordinate() {
        BigInteger y = BigInteger.valueOf(67890);
        try {
            new ECPoint(null, y);
            assert false : "Expected NullPointerException for null x";
        } catch (NullPointerException e) {
            assert e.getMessage().contains("affine coordinate x or y is null") : "Unexpected exception message";
        }
    }

    static void testNullYCoordinate() {
        BigInteger x = BigInteger.valueOf(12345);
        try {
            new ECPoint(x, null);
            assert false : "Expected NullPointerException for null y";
        } catch (NullPointerException e) {
            assert e.getMessage().contains("affine coordinate x or y is null") : "Unexpected exception message";
        }
    }

    static void testPointInfinity() {
        ECPoint infinity = ECPoint.POINT_INFINITY;
        assert infinity.getAffineX() == null : "POINT_INFINITY should have null x";
        assert infinity.getAffineY() == null : "POINT_INFINITY should have null y";
    }
}
