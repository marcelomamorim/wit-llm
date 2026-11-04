/*
 * @test
 * @summary Test URLClassLoader constructor with AccessControlContext for basic behavior and security checks
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
import java.security.PrivilegedAction;
import java.security.SecurityManager;

public class URLClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidArgs();
        testConstructorWithNullURLs();
        testConstructorSecurityManagerCheck();
    }

    private static void testConstructorWithValidArgs() throws Exception {
        URL[] urls = new URL[0];
        AccessControlContext acc = AccessController.getContext();
        URLClassLoader loader = new URLClassLoader(urls, acc);
        if (loader == null) {
            throw new RuntimeException("URLClassLoader constructor returned null");
        }
    }

    private static void testConstructorWithNullURLs() throws Exception {
        // The constructor does not explicitly check for null URLs array,
        // but passing null will cause NullPointerException in URLClassPath constructor.
        // We test that behavior here.
        try {
            new URLClassLoader(null, AccessController.getContext());
            throw new RuntimeException("Expected NullPointerException not thrown when URLs is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorSecurityManagerCheck() throws Exception {
        // Install a SecurityManager that denies createClassLoader permission
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkCreateClassLoader() {
                throw new SecurityException("createClassLoader not allowed");
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow everything else
            }
        });

        try {
            URL[] urls = new URL[0];
            AccessControlContext acc = AccessController.getContext();
            try {
                new URLClassLoader(urls, acc);
                throw new RuntimeException("Expected SecurityException not thrown");
            } catch (SecurityException expected) {
                // expected
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
