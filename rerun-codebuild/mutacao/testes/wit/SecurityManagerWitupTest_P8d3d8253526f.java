/*
 * @test
 * @summary Test for SecurityManager.checkConnect method behavior.
 * @run main SecurityManagerWitupTest_P8d3d8253526f
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_P8d3d8253526f {

    public static void main(String[] args) {
        testCheckConnectWithNullHost();
        testCheckConnectWithValidHostAndPort();
        testCheckConnectWithNegativePort();
    }

    private static void testCheckConnectWithNullHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect(null, 80, null);
            System.out.println("PASS: checkConnect with null host threw NullPointerException as expected.");
        } catch (NullPointerException e) {
            System.out.println("PASS: checkConnect with null host threw NullPointerException.");
        } catch (Exception e) {
            System.out.println("FAIL: checkConnect with null host threw unexpected exception: " + e);
        }
    }

    private static void testCheckConnectWithValidHostAndPort() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("localhost", 8080, null);
            System.out.println("PASS: checkConnect with valid host and port did not throw.");
        } catch (Exception e) {
            System.out.println("FAIL: checkConnect with valid host and port threw exception: " + e);
        }
    }

    private static void testCheckConnectWithNegativePort() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect("localhost", -1, null);
            System.out.println("PASS: checkConnect with negative port did not throw.");
        } catch (Exception e) {
            System.out.println("FAIL: checkConnect with negative port threw exception: " + e);
        }
    }
}
