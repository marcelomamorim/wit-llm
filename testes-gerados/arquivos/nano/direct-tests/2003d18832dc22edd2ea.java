/* @test    @summary Test ECPublicKeySpec constructor with valid parameters    @run main ECPublicKeySpecWitupTest */

import java.security.spec.ECPoint;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;

public class ECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullW();
        testNullParams();
        testPointAtInfinity();
    }

    static void testValidConstructor() {
        ECPoint point = new ECPoint(1, 2);
        ECParameterSpec params = null;
        try {
            params = new ECParameterSpec(); // Assuming default constructor exists for test
        } catch (Exception e) {
            // If no default constructor, create a mock or dummy object
            params = new ECParameterSpec() {
                // minimal implementation
            };
        }
        ECPublicKeySpec spec = new ECPublicKeySpec(point, params);
        assert spec.getW() == point : "W point mismatch";
        assert spec.getParams() == params : "Params mismatch";
    }

    static void testNullW() {
        ECParameterSpec params = new ECParameterSpec();
        try {
            new ECPublicKeySpec(null, params);
            assert false : "Expected NullPointerException for null W";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("w is null") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testNullParams() {
        ECPoint point = new ECPoint(1, 2);
        try {
            new ECPublicKeySpec(point, null);
            assert false : "Expected NullPointerException for null params";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("params is null") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testPointAtInfinity() {
        ECPoint infinity = ECPoint.POINT_INFINITY;
        ECParameterSpec params = new ECParameterSpec();
        try {
            new ECPublicKeySpec(infinity, params);
            assert false : "Expected IllegalArgumentException for POINT_INFINITY";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("w is ECPoint.POINT_INFINITY") : "Unexpected message: " + e.getMessage();
        }
    }
}
