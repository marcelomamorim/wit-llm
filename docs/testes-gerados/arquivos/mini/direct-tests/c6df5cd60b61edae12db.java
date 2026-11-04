/*
 * @test
 * @summary Test XECPublicKeySpec constructor and accessors
 * @run main XECPublicKeySpecWitupTest
 */

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPublicKeySpec;

public class XECPublicKeySpecWitupTest {

    static class DummyParams implements AlgorithmParameterSpec {}

    public static void main(String[] args) {
        testConstructorAndAccessors();
        testConstructorNullParams();
        testConstructorNullU();
    }

    private static void testConstructorAndAccessors() {
        AlgorithmParameterSpec params = new DummyParams();
        BigInteger u = BigInteger.valueOf(123456789L);
        XECPublicKeySpec spec = new XECPublicKeySpec(params, u);

        if (spec.getParams() != params) {
            throw new AssertionError("getParams() did not return the expected params instance");
        }
        if (!spec.getU().equals(u)) {
            throw new AssertionError("getU() did not return the expected BigInteger value");
        }
    }

    private static void testConstructorNullParams() {
        try {
            new XECPublicKeySpec(null, BigInteger.ONE);
            throw new AssertionError("Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("params")) {
                throw new AssertionError("NullPointerException message does not mention 'params'");
            }
        }
    }

    private static void testConstructorNullU() {
        try {
            new XECPublicKeySpec(new DummyParams(), null);
            throw new AssertionError("Expected NullPointerException for null u");
        } catch (NullPointerException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("u")) {
                throw new AssertionError("NullPointerException message does not mention 'u'");
            }
        }
    }
}
