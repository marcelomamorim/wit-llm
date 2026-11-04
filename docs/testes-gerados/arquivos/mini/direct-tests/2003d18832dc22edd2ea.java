/*
 * @test
 * @summary Test ECPublicKeySpec constructor for valid and invalid inputs
 * @run main ECPublicKeySpecWitupTest
 */

import java.security.spec.ECPoint;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.math.BigInteger;

public class ECPublicKeySpecWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullW();
        testNullParams();
        testPointInfinityW();
    }

    private static void testValidConstruction() {
        ECPoint w = new ECPoint(BigInteger.ONE, BigInteger.TEN);
        EllipticCurve curve = new EllipticCurve(null, null, null);
        ECParameterSpec params = new ECParameterSpec(curve, w, BigInteger.TEN, 1);
        ECPublicKeySpec spec = new ECPublicKeySpec(w, params);
        if (!spec.getW().equals(w)) {
            throw new RuntimeException("getW() did not return the expected ECPoint");
        }
        if (!spec.getParams().equals(params)) {
            throw new RuntimeException("getParams() did not return the expected ECParameterSpec");
        }
    }

    private static void testNullW() {
        try {
            ECParameterSpec params = new ECParameterSpec(
                new EllipticCurve(null, null, null),
                new ECPoint(BigInteger.ONE, BigInteger.TEN),
                BigInteger.TEN, 1);
            new ECPublicKeySpec(null, params);
            throw new RuntimeException("Expected NullPointerException for null w");
        } catch (NullPointerException e) {
            if (!"w is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException for null w: " + e.getMessage());
            }
        }
    }

    private static void testNullParams() {
        try {
            ECPoint w = new ECPoint(BigInteger.ONE, BigInteger.TEN);
            new ECPublicKeySpec(w, null);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException for null params: " + e.getMessage());
            }
        }
    }

    private static void testPointInfinityW() {
        try {
            ECPoint w = ECPoint.POINT_INFINITY;
            ECParameterSpec params = new ECParameterSpec(
                new EllipticCurve(null, null, null),
                new ECPoint(BigInteger.ONE, BigInteger.TEN),
                BigInteger.TEN, 1);
            new ECPublicKeySpec(w, params);
            throw new RuntimeException("Expected IllegalArgumentException for w == POINT_INFINITY");
        } catch (IllegalArgumentException e) {
            if (!"w is ECPoint.POINT_INFINITY".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for IllegalArgumentException for POINT_INFINITY w: " + e.getMessage());
            }
        }
    }
}
