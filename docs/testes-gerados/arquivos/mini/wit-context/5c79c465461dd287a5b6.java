/*
 * @test
 * @summary Test sun.net.ResourceManager.beforeUdpCreate() behavior including limit enforcement
 * @run main ResourceManagerWitupTest
 */

import java.net.SocketException;
import sun.net.ResourceManager;

public class ResourceManagerWitupTest {

    public static void main(String[] args) throws Exception {
        testBeforeUdpCreateWithoutSecurityManager();
        testBeforeUdpCreateWithSecurityManagerLimit();
    }

    private static void testBeforeUdpCreateWithoutSecurityManager() throws Exception {
        // Save original security manager
        final SecurityManager original = System.getSecurityManager();
        try {
            // Remove security manager to test that no exception is thrown and no limit is enforced
            System.setSecurityManager(null);
            // Call multiple times, should never throw
            for (int i = 0; i < 100; i++) {
                ResourceManager.beforeUdpCreate();
            }
        } finally {
            // Restore original security manager
            System.setSecurityManager(original);
        }
    }

    private static void testBeforeUdpCreateWithSecurityManagerLimit() throws Exception {
        // Save original security manager
        final SecurityManager original = System.getSecurityManager();
        try {
            // Install a dummy security manager to enable limit enforcement
            System.setSecurityManager(new SecurityManager());

            // We do not have direct access to maxSockets or numSockets, so we try to exhaust the limit
            // The limit is at least 25 (default), so try up to 30 to trigger exception
            int successCount = 0;
            boolean exceptionThrown = false;
            for (int i = 0; i < 30; i++) {
                try {
                    ResourceManager.beforeUdpCreate();
                    successCount++;
                } catch (SocketException se) {
                    // Expected when limit exceeded
                    if ("maximum number of DatagramSockets reached".equals(se.getMessage())) {
                        exceptionThrown = true;
                        break;
                    } else {
                        throw se; // unexpected message
                    }
                }
            }

            if (!exceptionThrown) {
                throw new RuntimeException("Expected SocketException was not thrown when exceeding max sockets");
            }

            // After exception, call afterUdpClose to decrement count and allow another beforeUdpCreate call
            ResourceManager.afterUdpClose();

            // Now beforeUdpCreate should succeed again
            ResourceManager.beforeUdpCreate();

        } finally {
            // Restore original security manager
            System.setSecurityManager(original);
        }
    }
}
