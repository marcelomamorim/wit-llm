/*
 * @test
 * @summary Witup-generated tests for XECPublicKeySpec constructor null checks and getters
 * @run main XECPublicKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPublicKeySpec;

public class XECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testNullParamsThrowsNpe();
        testNullUThrowsNpe();
        testBothNullReportsParamsFirst();
        testValidArgumentsAreStored();
        testNegativeUIsAccepted();
        testLargeUIsAccepted();
    }

    static void testNullParamsThrowsNpe() {
        AlgorithmParameterSpec params = null;
        try {
            new XECPublicKeySpec(params, BigInteger.ONE);
        } catch (NullPointerException e) {
            check("params must not be null".equals(e.getMessage()),
                  "unexpected message for null params: " + e.getMessage());
            return;
        }
        throw new RuntimeException("expected NullPointerException for null params");
    }

    static void testNullUThrowsNpe() {
        AlgorithmParameterSpec params = new AlgorithmParameterSpec() { };
        try {
            new XECPublicKeySpec(params, null);
        } catch (NullPointerException e) {
            check("u must not be null".equals(e.getMessage()),
                  "unexpected message for null u: " + e.getMessage());
            return;
        }
        throw new RuntimeException("expected NullPointerException for null u");
    }

    static void testBothNullReportsParamsFirst() {
        try {
            new XECPublicKeySpec(null, null);
        } catch (NullPointerException e) {
            check("params must not be null".equals(e.getMessage()),
                  "expected params check first, got: " + e.getMessage());
            return;
        }
        throw new RuntimeException("expected NullPointerException when both are null");
    }

    static void testValidArgumentsAreStored() {
        AlgorithmParameterSpec params = new AlgorithmParameterSpec() { };
        BigInteger u = BigInteger.valueOf(9);
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);
        check(spec.getParams() == params, "getParams should return the same params instance");
        check(spec.getU() == u, "getU should return the same u instance");
    }

    static void testNegativeUIsAccepted() {
        AlgorithmParameterSpec params = new AlgorithmParameterSpec() { };
        BigInteger u = BigInteger.valueOf(-5);
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);
        check(spec.getU().equals(BigInteger.valueOf(-5)), "negative u should be preserved");
    }

    static void testLargeUIsAccepted() {
        AlgorithmParameterSpec params = new AlgorithmParameterSpec() { };
        BigInteger u = BigInteger.ONE.shiftLeft(255);
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);
        check(spec.getU().equals(u), "large u should be preserved without range restriction");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
