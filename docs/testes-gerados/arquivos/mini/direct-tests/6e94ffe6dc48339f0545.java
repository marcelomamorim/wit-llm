/*
 * @test
 * @summary Test ECPrivateKeySpec constructor and accessors
 * @run main ECPrivateKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.security.spec.ECFieldFp;

public class ECPrivateKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorAndAccessors();
        testConstructorNullS();
        testConstructorNullParams();
    }

    private static void testConstructorAndAccessors() {
        BigInteger s = BigInteger.valueOf(123456789L);
        ECParameterSpec params = createSampleECParameterSpec();
        ECPrivateKeySpec keySpec = new ECPrivateKeySpec(s, params);

        if (!keySpec.getS().equals(s)) {
            throw new RuntimeException("getS() returned unexpected value");
        }
        if (keySpec.getParams() != params) {
            throw new RuntimeException("getParams() returned unexpected value");
        }
    }

    private static void testConstructorNullS() {
        ECParameterSpec params = createSampleECParameterSpec();
        try {
            new ECPrivateKeySpec(null, params);
            throw new RuntimeException("Expected NullPointerException for null s");
        } catch (NullPointerException e) {
            if (!"s is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException for null s: " + e.getMessage());
            }
        }
    }

    private static void testConstructorNullParams() {
        BigInteger s = BigInteger.ONE;
        try {
            new ECPrivateKeySpec(s, null);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for NullPointerException for null params: " + e.getMessage());
            }
        }
    }

    private static ECParameterSpec createSampleECParameterSpec() {
        // Create a simple ECParameterSpec with minimal valid parameters
        // Curve: y^2 = x^3 + ax + b over prime field p
        BigInteger p = new BigInteger("23");
        ECFieldFp field = new ECFieldFp(p);
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("1");
        EllipticCurve curve = new EllipticCurve(field, a, b);
        // Base point G
        java.security.spec.ECPoint g = new java.security.spec.ECPoint(BigInteger.valueOf(3), BigInteger.valueOf(10));
        BigInteger n = new BigInteger("29"); // order
        int h = 1; // cofactor
        return new ECParameterSpec(curve, g, n, h);
    }
}
