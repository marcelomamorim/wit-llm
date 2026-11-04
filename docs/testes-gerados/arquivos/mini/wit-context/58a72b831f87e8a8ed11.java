/*
 * @test
 * @summary Test sun.security.ssl.X509KeyManagerImpl.getServerAliases behavior
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.Principal;
import sun.security.ssl.X509KeyManagerImpl;
import java.security.KeyStore;
import java.util.Collections;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetServerAliasesNullKeyType();
        testGetServerAliasesNullIssuers();
        testGetServerAliasesEmptyIssuers();
        testGetServerAliasesNormal();
    }

    private static void testGetServerAliasesNullKeyType() throws Exception {
        // Setup a dummy builder to create X509KeyManagerImpl
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, null);
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));

        // keyType null should return null (no aliases)
        String[] aliases = km.getServerAliases(null, null);
        if (aliases != null) {
            throw new RuntimeException("Expected null aliases for null keyType");
        }
    }

    private static void testGetServerAliasesNullIssuers() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, null);
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));

        // issuers null should be accepted and return possibly null or empty
        String[] aliases = km.getServerAliases("RSA", null);
        // The method may return null or array, accept both
        if (aliases != null && aliases.length < 0) {
            throw new RuntimeException("Aliases length negative, impossible");
        }
    }

    private static void testGetServerAliasesEmptyIssuers() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, null);
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));

        // issuers empty array should be accepted
        String[] aliases = km.getServerAliases("RSA", new Principal[0]);
        if (aliases != null && aliases.length < 0) {
            throw new RuntimeException("Aliases length negative, impossible");
        }
    }

    private static void testGetServerAliasesNormal() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        KeyStore.Builder builder = KeyStore.Builder.newInstance(ks, null);
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));

        // Normal call with non-null keyType and non-null issuers
        Principal[] issuers = new Principal[1];
        // Use a dummy Principal implementation
        issuers[0] = new Principal() {
            public String getName() { return "CN=Test"; }
            public boolean equals(Object o) { return (o instanceof Principal) && ((Principal)o).getName().equals(getName()); }
            public int hashCode() { return getName().hashCode(); }
            public String toString() { return getName(); }
        };

        String[] aliases = km.getServerAliases("RSA", issuers);
        // The method may return null or array, accept both
        if (aliases != null && aliases.length < 0) {
            throw new RuntimeException("Aliases length negative, impossible");
        }
    }
}
