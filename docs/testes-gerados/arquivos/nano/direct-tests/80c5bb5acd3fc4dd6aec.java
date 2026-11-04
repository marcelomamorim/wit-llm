/* @test    @summary Test TlsRsaPremasterSecretParameterSpec.run() method for different system property values    @run main TlsRsaPremasterSecretParameterSpecWitupTest */

import sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec;

public class TlsRsaPremasterSecretParameterSpecWitupTest {

    public static void main(String[] args) {
        testRunReturnsTrueWhenPropertyIsTrue();
        testRunReturnsFalseWhenPropertyIsNull();
        testRunReturnsFalseWhenPropertyIsFalse();
    }

    private static void testRunReturnsTrueWhenPropertyIsTrue() {
        String originalValue = System.getProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");
        try {
            boolean result = new TlsRsaPremasterSecretParameterSpec(1, 1).run().booleanValue();
            if (!result) {
                throw new AssertionError("Expected true when property is 'true'");
            }
        } finally {
            if (originalValue != null) {
                System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", originalValue);
            } else {
                System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
            }
        }
    }

    private static void testRunReturnsFalseWhenPropertyIsNull() {
        String originalValue = System.getProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        try {
            boolean result = new TlsRsaPremasterSecretParameterSpec(1, 1).run().booleanValue();
            if (result) {
                throw new AssertionError("Expected false when property is null");
            }
        } finally {
            if (originalValue != null) {
                System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", originalValue);
            }
        }
    }

    private static void testRunReturnsFalseWhenPropertyIsFalse() {
        String originalValue = System.getProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "false");
        try {
            boolean result = new TlsRsaPremasterSecretParameterSpec(1, 1).run().booleanValue();
            if (result) {
                throw new AssertionError("Expected false when property is 'false'");
            }
        } finally {
            if (originalValue != null) {
                System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", originalValue);
            } else {
                System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
            }
        }
    }
}
