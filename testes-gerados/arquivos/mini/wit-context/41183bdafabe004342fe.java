/*
 * @test
 * @summary Test SecurityManager.checkAccept(String, int) for null host and normal behavior
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
            sm.checkAccept(null, 80);
            throw new RuntimeException("Expected NullPointerException for null host");
        } catch (NullPointerException npe) {
            if (!"host can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test that a normal host without colon is accepted (no exception)
        sm.checkAccept("localhost", 8080);

        // Test that a host with colon but not starting with '[' is wrapped with brackets
        // We cannot directly observe the internal call, but no exception means success
        sm.checkAccept("fe80::1", 8080);

        // Test that a host starting with '[' is accepted as is
        sm.checkAccept("[fe80::1]", 8080);
    }
}
