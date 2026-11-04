/*
 * @test
 * @summary Test sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec run() method behavior
 * @run main TlsRsaPremasterSecretParameterSpecWitupTest
 */

import sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec;

public class TlsRsaPremasterSecretParameterSpecWitupTest {
    public static void main(String[] args) {
        TlsRsaPremasterSecretParameterSpec spec = new TlsRsaPremasterSecretParameterSpec(0, 0);
        Boolean result = spec.run();
        if (result == null) {
            throw new RuntimeException("run() returned null, expected non-null Boolean");
        }
        // The run() method returns Boolean.TRUE if the system property com.sun.net.ssl.rsaPreMasterSecretFix is set to "true" (case insensitive), else Boolean.FALSE.
        // We test the default behavior (likely false) and also test setting the property.

        // Test default behavior: property not set or set to non-true value
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        Boolean defaultResult = spec.run();
        if (defaultResult == null) {
            throw new RuntimeException("run() returned null on default property unset");
        }

        // Test property set to "true"
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");
        Boolean trueResult = spec.run();
        if (!Boolean.TRUE.equals(trueResult)) {
            throw new RuntimeException("run() did not return Boolean.TRUE when property set to 'true'");
        }

        // Test property set to "TRUE" (case insensitive)
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "TRUE");
        Boolean trueResultUpper = spec.run();
        if (!Boolean.TRUE.equals(trueResultUpper)) {
            throw new RuntimeException("run() did not return Boolean.TRUE when property set to 'TRUE'");
        }

        // Test property set to "false"
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "false");
        Boolean falseResult = spec.run();
        if (!Boolean.FALSE.equals(falseResult)) {
            throw new RuntimeException("run() did not return Boolean.FALSE when property set to 'false'");
        }

        // Test property set to empty string
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "");
        Boolean emptyResult = spec.run();
        if (!Boolean.FALSE.equals(emptyResult)) {
            throw new RuntimeException("run() did not return Boolean.FALSE when property set to empty string");
        }

        System.out.println("All tests passed.");
    }
}
