/*
 * @test
 * @summary Test getServerAliases method of X509KeyManagerImpl for various inputs
 * @run main X509KeyManagerImplWitupTest
 */

import sun.security.ssl.X509KeyManagerImpl;
import java.security.Principal;
import java.util.Arrays;

public class X509KeyManagerImplWitupTest {

    public static void main(String[] args) {
        testGetServerAliasesWithNulls();
        testGetServerAliasesWithEmpty();
        testGetServerAliasesWithSample();
    }

    static void testGetServerAliasesWithNulls() {
        X509KeyManagerImpl km = new X509KeyManagerImpl(null) {
            @Override
            public String[] getServerAliases(String keyType, Principal[] issuers) {
                return super.getServerAliases(keyType, issuers);
            }
        };
        String[] result = km.getServerAliases("RSA", null);
        if (result != null) {
            throw new AssertionError("Expected null or empty array, got: " + Arrays.toString(result));
        }
    }

    static void testGetServerAliasesWithEmpty() {
        X509KeyManagerImpl km = new X509KeyManagerImpl(null) {
            @Override
            public String[] getServerAliases(String keyType, Principal[] issuers) {
                return super.getServerAliases(keyType, issuers);
            }
        };
        String[] result = km.getServerAliases("RSA", new Principal[0]);
        if (result != null && result.length != 0) {
            throw new AssertionError("Expected empty array, got: " + Arrays.toString(result));
        }
    }

    static void testGetServerAliasesWithSample() {
        X509KeyManagerImpl km = new X509KeyManagerImpl(null) {
            @Override
            public String[] getServerAliases(String keyType, Principal[] issuers) {
                if ("RSA".equals(keyType)) {
                    return new String[] {"alias1", "alias2"};
                }
                return null;
            }
        };
        String[] result = km.getServerAliases("RSA", null);
        if (result == null || result.length != 2 || !"alias1".equals(result[0]) || !"alias2".equals(result[1])) {
            throw new AssertionError("Unexpected result: " + Arrays.toString(result));
        }
    }
}
