/*
 * @test
 * @summary Test sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec$1.run() method behavior
 * @run main TlsRsaPremasterSecretParameterSpecWitupTest
 */

public class TlsRsaPremasterSecretParameterSpecWitupTest {
    public static void main(String[] args) throws Exception {
        testRunReturnsTrueWhenPropertyIsTrue();
        testRunReturnsFalseWhenPropertyIsFalse();
        testRunReturnsFalseWhenPropertyIsNull();
    }

    private static void testRunReturnsTrueWhenPropertyIsTrue() {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");
        Boolean result = new sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec$1().run();
        if (!Boolean.TRUE.equals(result)) {
            throw new AssertionError("Expected Boolean.TRUE when property is 'true', got: " + result);
        }
    }

    private static void testRunReturnsFalseWhenPropertyIsFalse() {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "false");
        Boolean result = new sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec$1().run();
        if (!Boolean.FALSE.equals(result)) {
            throw new AssertionError("Expected Boolean.FALSE when property is 'false', got: " + result);
        }
    }

    private static void testRunReturnsFalseWhenPropertyIsNull() {
        System.clearProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
        Boolean result = new sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec$1().run();
        if (!Boolean.FALSE.equals(result)) {
            throw new AssertionError("Expected Boolean.FALSE when property is null, got: " + result);
        }
    }

    // Inner class to access the private anonymous class implementing PrivilegedAction<Boolean>
    // This is necessary because the run() method is inside an anonymous class.
    // The class name is sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec$1 as per JLS for anonymous classes.
    static class TlsRsaPremasterSecretParameterSpec$1 implements java.security.PrivilegedAction<Boolean> {
        public Boolean run() {
            String value = System.getProperty("com.sun.net.ssl.rsaPreMasterSecretFix");
            if (value != null && value.equalsIgnoreCase("true")) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
}
