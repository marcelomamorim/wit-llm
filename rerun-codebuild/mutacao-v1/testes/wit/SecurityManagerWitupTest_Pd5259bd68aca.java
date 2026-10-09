/*
 * @test
 * @summary Test for SecurityManager.checkConnect method.
 * @run main SecurityManagerWitupTest_Pd5259bd68aca
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_Pd5259bd68aca {

    public static void main(String[] args) {
        testCheckConnectNullHost();
        testCheckConnectValidHostAndPort();
        testCheckConnectHostWithColonAndBrackets();
    }

    private static void testCheckConnectNullHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect(null, 80, null);
            throw new AssertionError("Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testCheckConnectValidHostAndPort() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("example.com", 80, null);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testCheckConnectHostWithColonAndBrackets() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("[::1]:8080", 8080, null);
            // No exception expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
