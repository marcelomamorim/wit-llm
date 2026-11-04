/*
 * @test
 * @summary Observable behavior of getClientAliases through the public NewSunX509 KeyManagerFactory
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.KeyStore;
import java.security.Principal;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.security.auth.x500.X500Principal;

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
            throw new RuntimeException("No X509KeyManager returned by NewSunX509");
        }

        String[] noIssuers = km.getClientAliases("RSA", null);
        if (noIssuers != null && noIssuers.length != 0) {
            throw new RuntimeException("Expected no aliases for empty keystore (null issuers)");
        }

        Principal[] issuers = { new X500Principal("CN=Test CA") };
        String[] withIssuers = km.getClientAliases("RSA", issuers);
        if (withIssuers != null && withIssuers.length != 0) {
            throw new RuntimeException("Expected no aliases for empty keystore (with issuers)");
        }

        Principal[] emptyIssuers = new Principal[0];
        String[] withEmpty = km.getClientAliases("RSA", emptyIssuers);
        if (withEmpty != null && withEmpty.length != 0) {
            throw new RuntimeException("Expected no aliases for empty keystore (empty issuers)");
        }
    }
}
