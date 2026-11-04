/* @test    @summary Test getProtectionParameter() returns the protection field value    @run main KeyStoreWitupTest */

import java.security.KeyStore;
import java.security.ProtectionParameter;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testGetProtectionParameterReturnsProtection();
    }

    private static void testGetProtectionParameterReturnsProtection() {
        ProtectionParameter protection = new ProtectionParameter() {};
        KeyStore keyStore = new KeyStore(null, null, "type") {
            private ProtectionParameter protectionField = protection;
            @Override
            public ProtectionParameter getProtectionParameter() {
                return protectionField;
            }
        };
        ProtectionParameter result = keyStore.getProtectionParameter();
        if (result != protection) {
            throw new AssertionError("Expected protection to be returned") ;
        }
    }
}
