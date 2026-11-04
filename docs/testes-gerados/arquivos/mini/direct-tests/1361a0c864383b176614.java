/*
 * @test
 * @summary Test java.lang.Class.forName(Module, String) method behavior
 * @run main ClassWitupTest
 */

import java.lang.Module;
import java.lang.Class;
import java.lang.SecurityManager;
import java.lang.reflect.InvocationTargetException;
import java.security.Permission;
import java.security.AccessControlException;

public class ClassWitupTest {

    public static void main(String[] args) throws Exception {
        testNullModule();
        testNullName();
        testLoadClassWithNullClassLoader();
        testLoadClassWithNonNullClassLoader();
        testSecurityManagerPermissionCheck();
    }

    private static void testNullModule() {
        try {
            Class.forName(null, "java.lang.String");
            throw new RuntimeException("Expected NullPointerException for null module");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullName() {
        Module m = ClassWitupTest.class.getModule();
        try {
            Class.forName(m, null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testLoadClassWithNullClassLoader() throws Exception {
        // Use BootLoader by passing a module with null class loader
        // We create a dummy module with null class loader by using the boot module
        Module bootModule = Object.class.getModule();
        Class<?> c = Class.forName(bootModule, "java.lang.String");
        if (!c.equals(String.class)) {
            throw new RuntimeException("Expected java.lang.String class");
        }
    }

    private static void testLoadClassWithNonNullClassLoader() throws Exception {
        // Use current class's module which has a non-null class loader
        Module m = ClassWitupTest.class.getModule();
        Class<?> c = Class.forName(m, "java.lang.String");
        if (!c.equals(String.class)) {
            throw new RuntimeException("Expected java.lang.String class");
        }
    }

    private static void testSecurityManagerPermissionCheck() throws Exception {
        // Install a SecurityManager that denies GET_CLASSLOADER_PERMISSION
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if ("getClassLoader".equals(perm.getName())) {
                    throw new SecurityException("Permission denied: " + perm);
                }
            }
            @Override
            public void checkPermission(Permission perm, Object context) {
                checkPermission(perm);
            }
        });

        try {
            Module m = ClassWitupTest.class.getModule();
            // The caller module is the same as the argument module, so no permission check
            Class<?> c = Class.forName(m, "java.lang.String");
            if (!c.equals(String.class)) {
                throw new RuntimeException("Expected java.lang.String class");
            }

            // Now call with a different module to trigger permission check
            Module bootModule = Object.class.getModule();
            try {
                Class.forName(bootModule, "java.lang.String");
                throw new RuntimeException("Expected SecurityException due to permission denial");
            } catch (SecurityException se) {
                // expected
            }

        } finally {
            System.setSecurityManager(original);
        }
    }
}