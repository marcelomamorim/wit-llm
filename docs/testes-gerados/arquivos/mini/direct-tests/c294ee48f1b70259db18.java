/*
 * @test
 * @summary Test javax.net.ssl.SSLContext.setDefault(SSLContext) method
 * @run main SSLContextWitupTest
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import java.security.Provider;
import java.security.SecurityManager;
import java.security.Permission;

public class SSLContextWitupTest {

    // Minimal SSLContextSpi stub for constructor
    static class DummySSLContextSpi extends SSLContextSpi {
        protected DummySSLContextSpi() {}
        protected void engineInit(KeyManager[] km, TrustManager[] tm, java.security.SecureRandom sr) {}
        protected SSLSocketFactory engineGetSocketFactory() { return null; }
        protected SSLServerSocketFactory engineGetServerSocketFactory() { return null; }
        protected SSLEngine engineCreateSSLEngine() { return null; }
        protected SSLEngine engineCreateSSLEngine(String host, int port) { return null; }
        protected SSLSessionContext engineGetServerSessionContext() { return null; }
        protected SSLSessionContext engineGetClientSessionContext() { return null; }
    }

    // Minimal Provider stub
    static class DummyProvider extends Provider {
        DummyProvider() {
            super("DummyProvider", 1.0, "Dummy Provider for testing");
        }
    }

    public static void main(String[] args) throws Exception {
        testSetDefaultNullContext();
        testSetDefaultSecurityManagerPermission();
        testSetDefaultSuccess();
    }

    private static void testSetDefaultNullContext() {
        try {
            SSLContext.setDefault(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testSetDefaultSecurityManagerPermission() throws Exception {
        // Install a SecurityManager that denies the SSLPermission "setDefaultSSLContext"
        SecurityManager originalSM = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof javax.net.ssl.SSLPermission && "setDefaultSSLContext".equals(perm.getName())) {
                    throw new SecurityException("Permission denied");
                }
            }
        });

        SSLContext ctx = new SSLContext(new DummySSLContextSpi(), new DummyProvider(), "TLSv1.2");

        try {
            SSLContext.setDefault(ctx);
            throw new RuntimeException("Expected SecurityException not thrown");
        } catch (SecurityException se) {
            // expected
        } finally {
            System.setSecurityManager(originalSM);
        }
    }

    private static void testSetDefaultSuccess() throws Exception {
        SSLContext ctx = new SSLContext(new DummySSLContextSpi(), new DummyProvider(), "TLSv1.2");
        SSLContext.setDefault(ctx);
        SSLContext def = SSLContext.getDefault();
        if (def != ctx) {
            throw new RuntimeException("setDefault did not set the default context correctly");
        }
    }
}
