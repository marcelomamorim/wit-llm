/*
 * @test
 * @summary Verify X509KeyManagerImpl.getServerAliases reports no server aliases for an empty KeyStore
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
            }
        }
        if (km == null) {
            throw new RuntimeException("No X509KeyManager returned");
        }

        checkNoAliases(km.getServerAliases("RSA", null));
        checkNoAliases(km.getServerAliases("RSA", new Principal[0]));
        checkNoAliases(km.getServerAliases("EC", null));
        checkNoAliases(km.getServerAliases("UNKNOWN_KEY_TYPE", null));
    }

    private static void checkNoAliases(String[] aliases) {
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected no server aliases but got " + aliases.length);
        }
    }
}
