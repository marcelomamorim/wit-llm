/*
 * @test
 * @summary Test SecurityManager.checkLink(String) for null and valid input
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckLinkNull();
        testCheckLinkValid();
    }

    static void testCheckLinkNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink(null);
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckLinkValid() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink("testLib");
            System.out.println("PASS: checkLink with valid input executed without exception");
        } catch (SecurityException se) {
            System.out.println("FAIL: SecurityException thrown unexpectedly");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
