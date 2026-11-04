/*
 * @test
 * @summary Test javax.net.ssl.SSLContext.setDefault(SSLContext) for null and security checks
 * @run main SSLContextWitupTest
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPermission;
import java.security.Permission;
import java.security.SecurityManager;

public class SSLContextWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultNullContext();
        testSetDefaultSecurityManagerPermission();
        testSetDefaultSuccess();
    }

    private static void testSetDefaultNullContext() {
        try {
            SSLContext.setDefault(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSetDefaultSecurityManagerPermission() throws Exception {
        final SecurityManager originalSM = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkPermission(Permission perm) {
                    if (perm instanceof SSLPermission && "setDefaultSSLContext".equals(perm.getName())) {
                        throw new SecurityException("Permission denied");
                    }
                }

                @Override
                public void checkPermission(Permission perm, Object context) {
                    checkPermission(perm);
                }
            });

            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            try {
                SSLContext.setDefault(ctx);
                throw new RuntimeException("Expected SecurityException not thrown");
            } catch (SecurityException expected) {
                // Expected exception
            }
        } finally {
            System.setSecurityManager(originalSM);
        }
    }

    private static void testSetDefaultSuccess() throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        SSLContext.setDefault(ctx);
        SSLContext def = SSLContext.getDefault();
        if (def != ctx) {
            throw new RuntimeException("Default SSLContext was not set correctly");
        }
    }
}
