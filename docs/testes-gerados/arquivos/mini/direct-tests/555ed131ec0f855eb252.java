/*
 * @test
 * @summary Test getClientAliases method of X509KeyManagerImpl
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.Principal;
import java.security.KeyStore;
import java.security.KeyStore.Builder;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import sun.security.ssl.X509KeyManagerImpl;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetClientAliasesWithNullKeyTypeAndNullIssuers();
        testGetClientAliasesWithEmptyKeyTypeAndNullIssuers();
        testGetClientAliasesWithValidKeyTypeAndNullIssuers();
        testGetClientAliasesWithValidKeyTypeAndEmptyIssuers();
    }

    private static void testGetClientAliasesWithNullKeyTypeAndNullIssuers() throws Exception {
        Builder builder = createEmptyBuilder();
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));
        String[] aliases = km.getClientAliases(null, null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected null or empty array for null keyType and null issuers");
        }
    }

    private static void testGetClientAliasesWithEmptyKeyTypeAndNullIssuers() throws Exception {
        Builder builder = createEmptyBuilder();
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));
        String[] aliases = km.getClientAliases("", null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected null or empty array for empty keyType and null issuers");
        }
    }

    private static void testGetClientAliasesWithValidKeyTypeAndNullIssuers() throws Exception {
        Builder builder = createEmptyBuilder();
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));
        String[] aliases = km.getClientAliases("RSA", null);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected null or empty array for valid keyType and null issuers with empty keystore");
        }
    }

    private static void testGetClientAliasesWithValidKeyTypeAndEmptyIssuers() throws Exception {
        Builder builder = createEmptyBuilder();
        X509KeyManagerImpl km = new X509KeyManagerImpl(Collections.singletonList(builder));
        String[] aliases = km.getClientAliases("RSA", new Principal[0]);
        if (aliases != null && aliases.length != 0) {
            throw new RuntimeException("Expected null or empty array for valid keyType and empty issuers with empty keystore");
        }
    }

    private static Builder createEmptyBuilder() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        return KeyStore.Builder.newInstance(ks, null);
    }
}
