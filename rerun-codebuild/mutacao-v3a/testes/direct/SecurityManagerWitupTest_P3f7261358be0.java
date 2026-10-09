/* @test    @summary Test SecurityManager.checkConnect with valid host and port    @run main SecurityManagerWitupTest_P3f7261358be0 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest_P3f7261358be0 {

    public static void main(String[] args) {
        testCheckConnectWithValidHostAndPort();
    }

    public static void testCheckConnectWithValidHostAndPort() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm, Object context) {
                // Allow all permissions for testing
                // Or add specific logic if needed
            }
        };
        // Use a valid host and port
        String host = "localhost";
        int port = 80;
        Object context = null;
        try {
            sm.checkConnect(host, port, context);
        } catch (SecurityException se) {
            throw new AssertionError("checkConnect threw SecurityException unexpectedly", se);
        }
    }
}
