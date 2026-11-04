/*
 * @test
 * @summary Test SecurityManager.checkAccess(ThreadGroup g) for null and root group
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.ThreadGroup;
import sun.security.util.SecurityConstants;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckAccessWithNull();
        testCheckAccessWithRootGroup();
    }

    static void testCheckAccessWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkAccess(null);
            // Expect NullPointerException
            throw new AssertionError("Expected NullPointerException for null ThreadGroup");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testCheckAccessWithRootGroup() {
        SecurityManager sm = new SecurityManager();
        ThreadGroup root = getRootThreadGroup();
        try {
            sm.checkAccess(root);
            // No exception expected
        } catch (SecurityException e) {
            throw new AssertionError("Unexpected SecurityException for root ThreadGroup", e);
        }
    }

    static ThreadGroup getRootThreadGroup() {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        while (tg.getParent() != null) {
            tg = tg.getParent();
        }
        return tg;
    }
}
