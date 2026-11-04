/*
 * @test
 * @summary getClientAliases on NewSunX509 key manager returns null when the KeyStore has no aliases
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("NewSunX509");
        kmf.init(ks, new char[0]);

        X509KeyManager km = null;
        for (KeyManager k : kmf.getKeyManagers()) {
            if (k instanceof X509KeyManager) {
                km = (X509KeyManager) k;
            }
        }
        if (km == null) {
            throw new RuntimeException("No X509KeyManager returned");
        }

        String[] rsaAliases = km.getClientAliases("RSA", null);
        if (rsaAliases != null) {
            throw new RuntimeException("Expected null for empty KeyStore, got length "
                    + rsaAliases.length);
        }

        String[] ecAliases = km.getClientAliases("EC", new java.security.Principal[0]);
        if (ecAliases != null) {
            throw new RuntimeException("Expected null for empty KeyStore with issuers");
        }
    }
}
