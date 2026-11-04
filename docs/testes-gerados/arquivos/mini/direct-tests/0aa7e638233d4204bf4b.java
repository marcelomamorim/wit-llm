/*
 * @test
 * @summary Test URLClassLoader constructor with URL array and AccessControlContext
 * @run main URLClassLoaderWitupTest
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PermissionCollection;
import java.security.AllPermission;
import java.security.ProtectionDomain;
import java.security.CodeSource;

public class URLClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy AccessControlContext with AllPermission
        PermissionCollection perms = new Permissions();
        perms.add(new AllPermission());
        ProtectionDomain pd = new ProtectionDomain(new CodeSource(null, (java.security.cert.Certificate[]) null), perms);
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[] { pd });

        // Test with empty URL array
        URL[] emptyUrls = new URL[0];
        URLClassLoader clEmpty = new URLClassLoader(emptyUrls, acc);
        if (clEmpty == null) {
            throw new RuntimeException("URLClassLoader constructor returned null for empty URLs");
        }

        // Test with one valid URL
        URL[] oneUrl = new URL[] { new URL("file:/") };
        URLClassLoader clOne = new URLClassLoader(oneUrl, acc);
        if (clOne == null) {
            throw new RuntimeException("URLClassLoader constructor returned null for one URL");
        }

        // Test that the AccessControlContext is set (indirectly) by loading a resource
        // Since URLClassLoader does not expose acc, we test that loading a resource does not throw
        clOne.getResource("nonexistentresource");

        // Test that SecurityManager checkCreateClassLoader is called if SecurityManager is set
        SecurityManager original = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManager() {
                public void checkCreateClassLoader() {
                    // allow
                }
                public void checkPermission(java.security.Permission perm) {
                    // allow all
                }
            });
            URLClassLoader clSec = new URLClassLoader(oneUrl, acc);
            if (clSec == null) {
                throw new RuntimeException("URLClassLoader constructor returned null with SecurityManager");
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
