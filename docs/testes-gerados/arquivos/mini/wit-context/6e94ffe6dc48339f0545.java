/*
 * @test
 * @summary Test ECPrivateKeySpec constructor for null argument exceptions and valid construction
 * @run main ECPrivateKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;

public class ECPrivateKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorNullS();
        testConstructorNullParams();
        testValidConstructionAndGetters();
    }

    private static void testConstructorNullS() {
        ECParameterSpec params = createDummyParams();
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

    private static void testValidConstructionAndGetters() {
        BigInteger s = new BigInteger("1234567890");
        ECParameterSpec params = createDummyParams();
        ECPrivateKeySpec keySpec = new ECPrivateKeySpec(s, params);
        if (!s.equals(keySpec.getS())) {
            throw new RuntimeException("getS() did not return the expected value");
        }
        if (params != keySpec.getParams()) {
            throw new RuntimeException("getParams() did not return the expected ECParameterSpec instance");
        }
    }

    private static ECParameterSpec createDummyParams() {
        // Create minimal valid ECParameterSpec
        // Use a simple prime field and curve
        ECFieldFp field = new ECFieldFp(new java.math.BigInteger("23"));
        EllipticCurve curve = new EllipticCurve(field, new java.math.BigInteger("1"), new java.math.BigInteger("1"));
        ECPoint generator = new ECPoint(new java.math.BigInteger("1"), new java.math.BigInteger("1"));
        java.math.BigInteger order = new java.math.BigInteger("29");
        int cofactor = 1;
        return new ECParameterSpec(curve, generator, order, cofactor);
    }
}
