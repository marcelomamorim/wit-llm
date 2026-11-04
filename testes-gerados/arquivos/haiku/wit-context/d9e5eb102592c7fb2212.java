/*
 * @test
 * @summary Verifies KeyStore.Builder.getProtectionParameter(String) returns the protection parameter given to newInstance and rejects a null alias
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;

public class KeyStoreWitupTest {

    public static void main(String[] args) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection("changeit".toCharArray());
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, pp);

        KeyStore.ProtectionParameter returned = builder.getProtectionParameter("alias1");
        if (returned != pp) {
            throw new RuntimeException("Expected the same protection parameter instance");
        }

        boolean npe = false;
        try {
            builder.getProtectionParameter(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null alias");
        }
    }
}
