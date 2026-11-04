/*
 * @test
 * @summary Test for SecurityManager.checkConnect(String, int)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckConnectNullHost();
        testCheckConnectValidHostAndPort();
        testCheckConnectHostWithColonAndBrackets();
    }

    private static void testCheckConnectNullHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect(null, 80);
            System.out.println("testCheckConnectNullHost: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testCheckConnectNullHost: PASSED");
        } catch (Exception e) {
            System.out.println("testCheckConnectNullHost: FAILED (unexpected exception: " + e + ")");
        }
    }

    private static void testCheckConnectValidHostAndPort() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("localhost", 8080);
            System.out.println("testCheckConnectValidHostAndPort: PASSED");
        } catch (Exception e) {
            System.out.println("testCheckConnectValidHostAndPort: FAILED (exception: " + e + ")");
        }
    }

    private static void testCheckConnectHostWithColonAndBrackets() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("[::1]:80", 80);
            System.out.println("testCheckConnectHostWithColonAndBrackets: PASSED");
        } catch (Exception e) {
            System.out.println("testCheckConnectHostWithColonAndBrackets: FAILED (exception: " + e + ")");
        }
    }
}
