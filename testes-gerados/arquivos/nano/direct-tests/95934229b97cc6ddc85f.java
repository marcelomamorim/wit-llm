/*
 * @test
 * @summary Test SecurityManager.checkConnect method for various host and port inputs.
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        if (sm == null) {
            System.err.println("SecurityManager is null");
            return;
        }
        testCheckConnectWithNullHost(sm);
        testCheckConnectWithEmptyHost(sm);
        testCheckConnectWithIPv4Host(sm);
        testCheckConnectWithIPv6Host(sm);
        testCheckConnectWithPort(sm);
        testCheckConnectWithNegativePort(sm);
        testCheckConnectWithMaxPort(sm);
    }

    static void testCheckConnectWithNullHost(SecurityManager sm) {
        try {
            sm.checkConnect(null, 80);
            System.err.println("Failed: Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testCheckConnectWithEmptyHost(SecurityManager sm) {
        try {
            sm.checkConnect("", 80);
            // Should pass, empty host is valid string
        } catch (SecurityException e) {
            System.err.println("Failed: Unexpected SecurityException for empty host");
        }
    }

    static void testCheckConnectWithIPv4Host(SecurityManager sm) {
        try {
            sm.checkConnect("127.0.0.1", 80);
        } catch (SecurityException e) {
            // Expected if no permission granted
        }
    }

    static void testCheckConnectWithIPv6Host(SecurityManager sm) {
        try {
            sm.checkConnect("::1", 80);
        } catch (SecurityException e) {
            // Expected if no permission granted
        }
    }

    static void testCheckConnectWithPort(SecurityManager sm) {
        try {
            sm.checkConnect("localhost", 8080);
        } catch (SecurityException e) {
            // Expected if no permission granted
        }
    }

    static void testCheckConnectWithNegativePort(SecurityManager sm) {
        try {
            sm.checkConnect("localhost", -1);
            System.err.println("Failed: Expected SecurityException for negative port");
        } catch (SecurityException e) {
            // Expected
        }
    }

    static void testCheckConnectWithMaxPort(SecurityManager sm) {
        try {
            sm.checkConnect("localhost", 65535);
        } catch (SecurityException e) {
            // Expected if no permission granted
        }
    }
}
