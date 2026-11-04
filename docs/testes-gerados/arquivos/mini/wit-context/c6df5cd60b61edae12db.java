/*
 * @test
 * @summary Test XECPublicKeySpec constructor null checks and getters
 * @run main XECPublicKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPublicKeySpec;

public class XECPublicKeySpecWitupTest {

    static class DummyParams implements AlgorithmParameterSpec {}

    public static void main(String[] args) {
        testConstructorRejectsNullParams();
        testConstructorRejectsNullU();
        testGettersReturnConstructorArgs();
    }

    private static void testConstructorRejectsNullParams() {
        try {
            new XECPublicKeySpec(null, BigInteger.ONE);
            throw new RuntimeException("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testConstructorRejectsNullU() {
        try {
            new XECPublicKeySpec(new DummyParams(), null);
            throw new RuntimeException("Expected NullPointerException for null u");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGettersReturnConstructorArgs() {
        DummyParams params = new DummyParams();
        BigInteger u = new BigInteger("1234567890");
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);
        if (spec.getParams() != params) {
            throw new RuntimeException("getParams() did not return the constructor argument");
        }
        if (!spec.getU().equals(u)) {
            throw new RuntimeException("getU() did not return the constructor argument");
        }
    }
}
