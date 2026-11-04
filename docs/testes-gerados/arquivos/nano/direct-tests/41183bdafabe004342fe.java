/*
 * @test    @summary Test SecurityManager.checkAccept method for valid and null host inputs
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.SecurityPermission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckAcceptWithValidHost();
        testCheckAcceptWithNullHost();
    }

    static void testCheckAcceptWithValidHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkAccept("localhost", 80);
            System.out.println("testCheckAcceptWithValidHost passed");
        } catch (SecurityException e) {
            System.err.println("testCheckAcceptWithValidHost failed: " + e);
        }
    }

    static void testCheckAcceptWithNullHost() {
        SecurityManager sm = new SecurityManager();
        boolean exceptionThrown = false;
        try {
            sm.checkAccept(null, 80);
        } catch (NullPointerException e) {
            exceptionThrown = true;
            System.out.println("testCheckAcceptWithNullHost passed");
        }
        if (!exceptionThrown) {
            System.err.println("testCheckAcceptWithNullHost failed: NullPointerException not thrown");
        }
    }
}
