/*
 * @test
 * @summary Test constructor of ECPublicKeySpec for null and invalid inputs
 * @run main ECPublicKeySpecWitupTest
 */

import java.security.spec.ECPublicKeySpec;
import java.security.spec.ECPoint;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.math.BigInteger;

public class ECPublicKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testNullWThrows();
        testNullParamsThrows();
        testPointInfinityWThrows();
        testValidConstruction();
    }

    private static void testNullWThrows() {
        ECParameterSpec params = createDummyParams();
        try {
            new ECPublicKeySpec(null, params);
            throw new RuntimeException("Expected NullPointerException for null w");
        } catch (NullPointerException e) {
            if (!"w is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null w: " + e.getMessage());
            }
        }
    }

    private static void testNullParamsThrows() {
        ECPoint w = new ECPoint(BigInteger.ONE, BigInteger.ONE);
        try {
            new ECPublicKeySpec(w, null);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null params: " + e.getMessage());
            }
        }
    }

    private static void testPointInfinityWThrows() {
        ECParameterSpec params = createDummyParams();
        try {
            new ECPublicKeySpec(ECPoint.POINT_INFINITY, params);
            throw new RuntimeException("Expected IllegalArgumentException for POINT_INFINITY w");
        } catch (IllegalArgumentException e) {
            if (!"w is ECPoint.POINT_INFINITY".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for POINT_INFINITY w: " + e.getMessage());
            }
        }
    }

    private static void testValidConstruction() {
        ECPoint w = new ECPoint(BigInteger.ONE, BigInteger.ONE);
        ECParameterSpec params = createDummyParams();
        ECPublicKeySpec spec = new ECPublicKeySpec(w, params);
        if (!spec.getW().equals(w)) {
            throw new RuntimeException("getW() did not return the expected ECPoint");
        }
        if (!spec.getParams().equals(params)) {
            throw new RuntimeException("getParams() did not return the expected ECParameterSpec");
        }
    }

    private static ECParameterSpec createDummyParams() {
        // Create a minimal valid ECParameterSpec with dummy curve and parameters
        EllipticCurve curve = new EllipticCurve(null, BigInteger.ONE, BigInteger.ONE);
        ECPoint g = new ECPoint(BigInteger.ONE, BigInteger.ONE);
        BigInteger n = BigInteger.ONE;
        int h = 1;
        return new ECParameterSpec(curve, g, n, h);
    }
}
