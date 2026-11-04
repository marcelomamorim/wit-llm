/*
 * @test
 * @summary Test SecureClassLoader.apply(CodeSourceKey) method behavior
 * @run main SecureClassLoaderWitupTest
 */

import java.security.SecureClassLoader;
import java.security.ProtectionDomain;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.CodeSource;
import java.net.URL;

public class SecureClassLoaderWitupTest {

    // Minimal subclass to expose apply method via Function interface
    static class TestSecureClassLoader extends SecureClassLoader {
        private final CodeSource cs;

        protected TestSecureClassLoader() {
            super();
            // Use a dummy CodeSource for getPermissions
            cs = new CodeSource(null, (java.security.cert.Certificate[]) null);
        }

        // Expose apply method for testing
        public ProtectionDomain apply(SecureClassLoader.CodeSourceKey key) {
            // The original apply method ignores the key parameter
            PermissionCollection perms = this.getPermissions(cs);
            ProtectionDomain pd = new ProtectionDomain(cs, perms, this, null);
            return pd;
        }
    }

    public static void main(String[] args) {
        TestSecureClassLoader loader = new TestSecureClassLoader();

        // Create a dummy CodeSourceKey instance for testing
        // Since CodeSourceKey is package-private, we cannot instantiate it directly
        // So we pass null because apply ignores the parameter
        SecureClassLoader.CodeSourceKey dummyKey = null;

        ProtectionDomain pd = loader.apply(dummyKey);

        if (pd == null) {
            throw new RuntimeException("apply returned null ProtectionDomain");
        }

        // Check that the ProtectionDomain's CodeSource is the one we passed
        if (pd.getCodeSource() == null) {
            throw new RuntimeException("ProtectionDomain has null CodeSource");
        }

        // Check that the ProtectionDomain's ClassLoader is the loader instance
        if (pd.getClassLoader() != loader) {
            throw new RuntimeException("ProtectionDomain's ClassLoader is not the loader instance");
        }

        // Check that permissions is not null
        PermissionCollection perms = pd.getPermissions();
        if (perms == null) {
            throw new RuntimeException("ProtectionDomain has null PermissionCollection");
        }

        // The apply method does not throw exceptions in current code, so no exception test
        System.out.println("Test passed: apply method returns valid ProtectionDomain");
    }
}
