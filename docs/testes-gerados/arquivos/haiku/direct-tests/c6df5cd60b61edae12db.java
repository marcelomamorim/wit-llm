/*
 * @test
 * @summary Behavioral tests for the XECPublicKeySpec constructor and getters
 * @run main XECPublicKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPublicKeySpec;

public class XECPublicKeySpecWitupTest {

    static final class DummyParams implements AlgorithmParameterSpec {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullParamsThrowsNPE() {
        try {
            new XECPublicKeySpec(null, BigInteger.ONE);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullUThrowsNPE() {
        try {
            new XECPublicKeySpec(new DummyParams(), null);
            throw new RuntimeException("Expected NullPointerException for null u");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBothNullThrowsNPE() {
        try {
            new XECPublicKeySpec(null, null);
            throw new RuntimeException("Expected NullPointerException for null params and u");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGettersReturnSameReferences() {
        DummyParams params = new DummyParams();
        BigInteger u = BigInteger.valueOf(9);
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);
        check(spec.getParams() == params, "getParams() must return the supplied params instance");
        check(spec.getU() == u, "getU() must return the supplied BigInteger instance");
    }

    static void testUNotRestrictedToCanonicalRange() {
        BigInteger negative = BigInteger.valueOf(-1);
        BigInteger huge = BigInteger.ONE.shiftLeft(600);
        XECPublicKeySpec s1 = new XECPublicKeySpec(new DummyParams(), negative);
        XECPublicKeySpec s2 = new XECPublicKeySpec(new DummyParams(), huge);
        check(s1.getU().equals(negative), "Negative u must be accepted and returned unchanged");
        check(s2.getU().equals(huge), "Out-of-field u must be accepted and returned unchanged");
    }

    static void testZeroUIsAccepted() {
        XECPublicKeySpec spec = new XECPublicKeySpec(new DummyParams(), BigInteger.ZERO);
        check(spec.getU().signum() == 0, "Zero u must be stored and returned");
    }

    public static void main(String[] args) {
        testNullParamsThrowsNPE();
        testNullUThrowsNPE();
        testBothNullThrowsNPE();
        testGettersReturnSameReferences();
        testUNotRestrictedToCanonicalRange();
        testZeroUIsAccepted();
    }
}
