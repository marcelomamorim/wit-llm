/*
 * @test
 * @summary Tests the URLClassLoader constructor with URL[] and AccessControlContext parameters.
 * @run main URLClassLoaderWitupTest
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.ProtectionDomain;
import java.security.Permissions;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.AllPermission;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullUrls();
        testConstructorWithNullAcc();
    }

    private static void testConstructorWithValidParameters() {
        try {
            URL[] urls = new URL[0];
            Permissions perms = new Permissions();
            perms.add(new AllPermission());
            CodeSource cs = new CodeSource(null, (java.security.cert.Certificate[]) null);
            ProtectionDomain pd = new ProtectionDomain(cs, perms);
            AccessControlContext acc = new AccessControlContext(new ProtectionDomain[] { pd });
            URLClassLoader loader = new URLClassLoader(urls, acc);
            assert loader != null : "Loader should not be null";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid constructor test", e);
        }
    }

    private static void testConstructorWithNullUrls() {
        try {
            URL[] urls = null;
            Permissions perms = new Permissions();
            perms.add(new AllPermission());
            CodeSource cs = new CodeSource(null, (java.security.cert.Certificate[]) null);
            ProtectionDomain pd = new ProtectionDomain(cs, perms);
            AccessControlContext acc = new AccessControlContext(new ProtectionDomain[] { pd });
            URLClassLoader loader = new URLClassLoader(urls, acc);
            assert loader != null : "Loader should be created even with null URLs";
        } catch (NullPointerException npe) {
            throw new AssertionError("Constructor should handle null URLs without exception", npe);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in null URLs test", e);
        }
    }

    private static void testConstructorWithNullAcc() {
        try {
            URL[] urls = new URL[0];
            URLClassLoader loader = new URLClassLoader(urls, null);
            assert loader != null : "Loader should be created with null AccessControlContext";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in null AccessControlContext test", e);
        }
    }
}
