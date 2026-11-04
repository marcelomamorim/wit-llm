/*
 * @test
 * @summary Test sun.security.ssl.X509KeyManagerImpl.getClientAliases behavior
 * @run main X509KeyManagerImplWitupTest
 */

import java.security.Principal;
import sun.security.ssl.X509KeyManagerImpl;
import java.security.KeyStore;
import java.security.KeyStore.Builder;
import java.util.Collections;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Setup a minimal Builder to create X509KeyManagerImpl instance
        // Since no public constructors or factories are evident, use a dummy KeyStore.Builder
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        Builder builder = Builder.newInstance(ks, null);

        X509KeyManagerImpl km = new X509KeyManagerImpl(builder);

        // Test 1: getClientAliases with null keyType returns null (no aliases)
        String[] result1 = km.getClientAliases(null, null);
        if (result1 != null) {
            throw new RuntimeException("Expected null for getClientAliases(null, null), got non-null");
        }

        // Test 2: getClientAliases with non-null keyType and null issuers returns null
        String[] result2 = km.getClientAliases("RSA", null);
        if (result2 != null) {
            throw new RuntimeException("Expected null for getClientAliases(\"RSA\", null), got non-null");
        }

        // Test 3: getClientAliases with non-null keyType and empty issuers returns null
        String[] result3 = km.getClientAliases("RSA", new Principal[0]);
        if (result3 != null) {
            throw new RuntimeException("Expected null for getClientAliases(\"RSA\", empty issuers), got non-null");
        }

        // Test 4: getClientAliases with non-null keyType and non-empty issuers returns null (no keys in keystore)
        Principal dummyPrincipal = new Principal() {
            public String getName() { return "CN=Dummy"; }
        };
        String[] result4 = km.getClientAliases("RSA", new Principal[] { dummyPrincipal });
        if (result4 != null) {
            throw new RuntimeException("Expected null for getClientAliases(\"RSA\", non-empty issuers), got non-null");
        }

        // No IllegalArgumentException expected for any tested inputs
        // The WIT expath suggesting IllegalArgumentException on oversized HashMap capacity is not reproducible here
        // because issuers.length / 0.75f + 1 cannot be negative for valid array length.

        System.out.println("All tests passed.");
    }
}
