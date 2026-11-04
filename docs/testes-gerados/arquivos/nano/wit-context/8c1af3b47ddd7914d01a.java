/*
 * @test
 * @summary Test for SecurityManager.checkAccess(ThreadGroup g)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.ThreadGroup;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckAccessWithNull();
        testCheckAccessWithRootGroup();
        testCheckAccessWithOtherGroup();
    }

    private static void testCheckAccessWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkAccess((ThreadGroup) null);
            System.out.println("testCheckAccessWithNull: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testCheckAccessWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckAccessWithNull: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testCheckAccessWithRootGroup() {
        SecurityManager sm = new SecurityManager();
        ThreadGroup rootGroup = new ThreadGroup("root");
        try {
            sm.checkAccess(rootGroup);
            System.out.println("testCheckAccessWithRootGroup: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckAccessWithRootGroup: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testCheckAccessWithOtherGroup() {
        SecurityManager sm = new SecurityManager();
        ThreadGroup tg = new ThreadGroup("child");
        try {
            sm.checkAccess(tg);
            System.out.println("testCheckAccessWithOtherGroup: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckAccessWithOtherGroup: FAILED (unexpected exception: " + t + ")");
        }
    }
}
