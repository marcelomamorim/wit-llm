/*
 * @test
 * @summary Test SecurityManager.checkConnect() method for various host and port inputs.
 * @run main SecurityManagerWitupTest_Pd5259bd68aca
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_Pd5259bd68aca {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        if (sm == null) {
            throw new RuntimeException("SecurityManager is null");
        }
        testCheckConnectWithNullHost(sm);
        testCheckConnectWithIPv6Host(sm);
        testCheckConnectWithPort(sm);
        testCheckConnectWithNegativePort(sm);
        System.out.println("All tests passed.");
    }

    static void testCheckConnectWithNullHost(SecurityManager sm) {
        try {
            sm.checkConnect(null, 80, null);
            throw new AssertionError("Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testCheckConnectWithIPv6Host(SecurityManager sm) {
        String ipv6Host = "::1";
        try {
            sm.checkConnect(ipv6Host, 80, null);
        } catch (SecurityException e) {
            // This is acceptable if permission is not granted
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException");
        }
    }

    static void testCheckConnectWithPort(SecurityManager sm) {
        String host = "localhost";
        int port = 8080;
        try {
            sm.checkConnect(host, port, null);
        } catch (SecurityException e) {
            // This is acceptable if permission is not granted
        }
    }

    static void testCheckConnectWithNegativePort(SecurityManager sm) {
        String host = "localhost";
        int port = -1;
        try {
            sm.checkConnect(host, port, null);
            throw new AssertionError("Expected SecurityException for negative port");
        } catch (SecurityException e) {
            // expected
        }
    }
}
