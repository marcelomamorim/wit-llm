/*
 * @test
 * @summary Test ClassLoader.run() method for security package access check
 * @run main ClassLoaderWitupTest
 */

import java.lang.SecurityManager;
import java.lang.ClassLoader;
import java.security.AccessControlException;

public class ClassLoaderWitupTest {

    static class TestSecurityManager extends SecurityManager {
        private String checkedPackage = null;

        @Override
        public void checkPackageAccess(String pkg) {
            checkedPackage = pkg;
            // For test, do not throw exception
        }

        public String getCheckedPackage() {
            return checkedPackage;
        }
    }

    public static void main(String[] args) {
        TestSecurityManager sm = new TestSecurityManager();
        System.setSecurityManager(sm);

        final String packageName = "java.lang";

        ClassLoader cl = new ClassLoader() {
            public Void run() {
                sm.checkPackageAccess(packageName);
                return null;
            }
        };

        // Call run and verify that checkPackageAccess was called with correct package
        Void result = cl.run();
        if (result != null) {
            throw new RuntimeException("Expected null return from run()");
        }

        if (!packageName.equals(sm.getCheckedPackage())) {
            throw new RuntimeException("Expected checkPackageAccess to be called with '" + packageName + "' but was '" + sm.getCheckedPackage() + "'");
        }

        System.out.println("Test passed");
    }
}
