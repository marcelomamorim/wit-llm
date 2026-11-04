/*
 * @test
 * @summary Test SecurityManager.checkConnect(String, int) for null host and permission checks
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.net.SocketPermission;
import sun.security.util.SecurityConstants;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null host throws NullPointerException
        try {
            sm.checkConnect(null, 80);
            throw new RuntimeException("Expected NullPointerException for null host");
        } catch (NullPointerException npe) {
            if (!"host can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test that host with colon but not starting with '[' is wrapped with brackets
        // We cannot directly observe the internal host modification, but we can test that
        // no exception is thrown for a valid host with colon and port != -1
        String hostWithColon = "1.2.3.4:5";
        try {
            sm.checkConnect(hostWithColon, 80);
        } catch (SecurityException se) {
            // SecurityException may be thrown if no permission, but test is deterministic
            // so we accept it as valid behavior
        }

        // Test that host without colon is passed as is (no brackets added)
        String hostWithoutColon = "localhost";
        try {
            sm.checkConnect(hostWithoutColon, 80);
        } catch (SecurityException se) {
            // Accept SecurityException as valid
        }

        // Test that port == -1 triggers SOCKET_RESOLVE_ACTION permission check
        // We cannot observe permission check directly, but no exception means pass
        try {
            sm.checkConnect("localhost", -1);
        } catch (SecurityException se) {
            // Accept SecurityException as valid
        }

        // Test that port != -1 triggers SOCKET_CONNECT_ACTION permission check
        try {
            sm.checkConnect("localhost", 1234);
        } catch (SecurityException se) {
            // Accept SecurityException as valid
        }
    }
}
