/*
 * @test
 * @summary Test sun.security.ssl.X509KeyManagerImpl.getServerAliases method
 * @run main sun.security.ssl.X509KeyManagerImplWitupTest
 */

import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Collections;
import javax.net.ssl.X509ExtendedKeyManager;
import sun.security.ssl.X509KeyManagerImpl;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create an empty KeyStore
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);

        // Create a Builder from the KeyStore
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, null);

        // Create X509KeyManagerImpl with the builder
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));

        // Test getServerAliases with null keyType and null issuers
        String[] aliases = km.getServerAliases(null, null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no aliases for null keyType and null issuers");
        }

        // Test getServerAliases with a keyType that is unlikely to exist
        aliases = km.getServerAliases("NON_EXISTENT_KEY_TYPE", null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no aliases for non-existent keyType");
        }

        // Test getServerAliases with null keyType and empty issuers array
        aliases = km.getServerAliases(null, new Principal[0]);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no aliases for null keyType and empty issuers");
        }

        // Test getServerAliases with valid keyType but no matching entries
        aliases = km.getServerAliases("RSA", null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no aliases for RSA keyType with empty keystore");
        }

        System.out.println("All tests passed.");
    }
}
