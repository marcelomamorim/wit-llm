/*
 * @test    @summary Test checkAccept method behavior with valid and null inputs    @run main SecurityManagerWitupTest_P8a6554d2dcf0
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_P8a6554d2dcf0 {

    public static void main(String[] args) {
        testCheckAcceptWithValidHost();
        testCheckAcceptWithNullHost();
    }

    static void testCheckAcceptWithValidHost() {
        SecurityManager sm = new SecurityManager();
        String host = "localhost";
        int port = 8080;
        try {
            sm.checkAccept(host, port);
        } catch (SecurityException se) {
            // The test expects no SecurityException for this scenario
            throw new RuntimeException("Unexpected SecurityException for valid host", se);
        }
    }

    static void testCheckAcceptWithNullHost() {
        SecurityManager sm = new SecurityManager();
        String host = null;
        int port = 8080;
        try {
            sm.checkAccept(host, port);
            throw new AssertionError("Expected NullPointerException for null host");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
