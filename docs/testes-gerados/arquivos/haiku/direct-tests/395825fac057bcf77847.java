/*
 * @test
 * @summary Verifies that SecureClassLoader.defineClass with a CodeSource attaches a ProtectionDomain built by the private apply() path
 * @run main SecureClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;

public class SecureClassLoaderWitupTest {

    static class Loader extends SecureClassLoader {
        Loader() {
            super(null);
        }

        Class<?> define(String name, byte[] b, CodeSource cs) {
            return defineClass(name, b, 0, b.length, cs);
        }
    }

    static byte[] ownClassBytes() throws Exception {
        try (InputStream in = SecureClassLoaderWitupTest.class
                .getResourceAsStream("SecureClassLoaderWitupTest.class")) {
            if (in == null) {
                throw new AssertionError("class bytes not found");
            }
            return in.readAllBytes();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = ownClassBytes();
        URL url = new URL("file:///tmp/witup/classes/");
        CodeSource cs = new CodeSource(url, (java.security.cert.Certificate[]) null);

        Loader loader = new Loader();
        Class<?> c = loader.define("SecureClassLoaderWitupTest", bytes, cs);

        ProtectionDomain pd = c.getProtectionDomain();
        check(pd != null, "ProtectionDomain must not be null");
        check(pd.getClassLoader() == loader,
              "ProtectionDomain class loader must be the defining loader");
        check(cs.equals(pd.getCodeSource()),
              "ProtectionDomain code source must match the CodeSource passed to defineClass");
        PermissionCollection perms = pd.getPermissions();
        check(perms != null, "PermissionCollection must not be null");

        Loader other = new Loader();
        Class<?> c2 = other.define("SecureClassLoaderWitupTest", bytes, cs);
        ProtectionDomain pd2 = c2.getProtectionDomain();
        check(pd2 != null && pd2.getClassLoader() == other,
              "second loader must attach its own ProtectionDomain");
        check(pd2 != pd,
              "ProtectionDomains of different loaders must not be shared");
    }
}
