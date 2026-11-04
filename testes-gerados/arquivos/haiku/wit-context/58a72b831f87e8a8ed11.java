/*
 * @test
 * @summary Verifies getServerAliases on the NewSunX509 key manager returns no aliases for an empty KeyStore and for null keyType or empty issuers
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.KeyStore;
import java.security.Principal;
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
        for (KeyManager m : kmf.getKeyManagers()) {
            if (m instanceof X509KeyManager) {
                km = (X509KeyManager) m;
                break;
            }
        }
        if (km == null) {
            throw new RuntimeException("No X509KeyManager returned");
        }

        check(km.getServerAliases(null, null), "null keyType, null issuers");
        check(km.getServerAliases(null, new Principal[0]), "null keyType, empty issuers");
        check(km.getServerAliases("RSA", new Principal[0]), "RSA, empty issuers");
        check(km.getServerAliases("RSA", null), "RSA, null issuers");
    }

    private static void check(String[] aliases, String label) {
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no aliases for " + label
                    + " but got " + aliases.length);
        }
    }
}
