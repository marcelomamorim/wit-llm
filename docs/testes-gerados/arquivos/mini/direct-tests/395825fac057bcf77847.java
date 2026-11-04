/*
 * @test
 * @summary Test SecureClassLoader.apply method behavior
 * @run main SecureClassLoaderWitupTest
 */

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.net.URL;

public class SecureClassLoaderWitupTest {

    static class TestSecureClassLoader extends SecureClassLoader {
        private final CodeSource cs;

        protected TestSecureClassLoader() {
            super();
            try {
                cs = new CodeSource(new URL("file:/tmp/"), (java.security.cert.Certificate[]) null);
            } catch (java.net.MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        // Expose apply method for testing
        public ProtectionDomain testApply(CodeSourceKey key) {
            return apply(key);
        }

        // Override getPermissions to return a known PermissionCollection
        @Override
        protected PermissionCollection getPermissions(CodeSource codesource) {
            return super.getPermissions(cs);
        }
    }

    // Dummy CodeSourceKey class to satisfy parameter
    static class CodeSourceKey {
        // no fields or methods needed for this test
    }

    public static void main(String[] args) throws Exception {
        TestSecureClassLoader loader = new TestSecureClassLoader();
        CodeSourceKey key = new CodeSourceKey();

        ProtectionDomain pd = loader.testApply(key);

        if (pd == null) {
            throw new RuntimeException("apply returned null ProtectionDomain");
        }

        if (pd.getClassLoader() != loader) {
            throw new RuntimeException("ProtectionDomain's class loader is not the SecureClassLoader instance");
        }

        if (pd.getCodeSource() == null) {
            throw new RuntimeException("ProtectionDomain's CodeSource is null");
        }

        PermissionCollection perms = pd.getPermissions();
        if (perms == null) {
            throw new RuntimeException("ProtectionDomain's PermissionCollection is null");
        }

        // Print success message
        System.out.println("Test passed: ProtectionDomain created with expected properties.");
    }
}
