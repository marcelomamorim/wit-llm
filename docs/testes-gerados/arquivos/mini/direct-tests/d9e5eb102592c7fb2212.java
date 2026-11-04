/*
 * @test
 * @summary Test KeyStore.getProtectionParameter() returns the expected ProtectionParameter
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;
import java.security.Provider;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy KeyStoreSpi implementation to pass to the protected constructor
        KeyStoreSpi dummySpi = new KeyStoreSpi() {
            public KeyStore.Entry engineGetEntry(String alias, ProtectionParameter protParam) {
                return null;
            }
            public void engineLoad(java.io.InputStream stream, char[] password) {
            }
            public void engineStore(java.io.OutputStream stream, char[] password) {
            }
            public java.security.cert.Certificate engineGetCertificate(String alias) {
                return null;
            }
            public java.security.cert.Certificate[] engineGetCertificateChain(String alias) {
                return null;
            }
            public java.security.Key engineGetKey(String alias, char[] password) {
                return null;
            }
            public boolean engineContainsAlias(String alias) {
                return false;
            }
            public void engineDeleteEntry(String alias) {
            }
            public void engineSetKeyEntry(String alias, java.security.Key key, char[] password, java.security.cert.Certificate[] chain) {
            }
            public void engineSetKeyEntry(String alias, byte[] key, java.security.cert.Certificate[] chain) {
            }
            public void engineSetCertificateEntry(String alias, java.security.cert.Certificate cert) {
            }
            public Enumeration<String> engineAliases() {
                return null;
            }
            public int engineSize() {
                return 0;
            }
            public boolean engineIsKeyEntry(String alias) {
                return false;
            }
            public boolean engineIsCertificateEntry(String alias) {
                return false;
            }
            public void engineLoad(java.security.KeyStore.LoadStoreParameter param) {
            }
        };

        Provider dummyProvider = new Provider("DummyProvider", 1.0, "Dummy Provider") {};

        // Create KeyStore instance using protected constructor
        KeyStore ks = new KeyStore(dummySpi, dummyProvider, "DummyType") {
            private ProtectionParameter protection = new ProtectionParameter() {};
            @Override
            public ProtectionParameter getProtectionParameter() {
                return protection;
            }
        };

        ProtectionParameter pp = ks.getProtectionParameter();

        if (pp == null) {
            throw new RuntimeException("getProtectionParameter() returned null");
        }

        // Check that repeated calls return the same instance
        ProtectionParameter pp2 = ks.getProtectionParameter();
        if (pp != pp2) {
            throw new RuntimeException("getProtectionParameter() returned different instances");
        }

        System.out.println("Test passed: getProtectionParameter() returns consistent ProtectionParameter instance.");
    }
}
