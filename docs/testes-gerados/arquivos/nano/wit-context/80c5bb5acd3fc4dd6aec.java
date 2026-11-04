/*
 * @test
 * @summary Test for sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec
 * @run main TlsRsaPremasterSecretParameterSpecWitupTest
 */

import sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec;

public class TlsRsaPremasterSecretParameterSpecWitupTest {

    public static void main(String[] args) {
        testRunMethodWithPropertyTrue();
        testRunMethodWithPropertyNull();
        testRunMethodWithPropertyFalse();
    }

    private static void testRunMethodWithPropertyTrue() {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");
        TlsRsaPremasterSecretParameterSpec instance = new TlsRsaPremasterSecretParameterSpec(1, 2);
        Boolean result = instance.run();
        if (result == null || !result.equals(Boolean.TRUE)) {
            throw new AssertionError("Expected run() to return Boolean.TRUE when property is 'true'");
        }
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
    }

    private static void testRunMethodWithPropertyNull() {
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        TlsRsaPremasterSecretParameterSpec instance = new TlsRsaPremasterSecretParameterSpec(1, 2);
        Boolean result = instance.run();
        if (result == null || !result.equals(Boolean.FALSE)) {
            throw new AssertionError("Expected run() to return Boolean.FALSE when property is null");
        }
    }

    private static void testRunMethodWithPropertyFalse() {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "false");
        TlsRsaPremasterSecretParameterSpec instance = new TlsRsaPremasterSecretParameterSpec(1, 2);
        Boolean result = instance.run();
        if (result == null || !result.equals(Boolean.FALSE)) {
            throw new AssertionError("Expected run() to return Boolean.FALSE when property is 'false'");
        }
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
    }
}
