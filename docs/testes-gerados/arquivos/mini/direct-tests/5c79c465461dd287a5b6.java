/*
 * @test
 * @summary Test sun.net.ResourceManager.beforeUdpCreate behavior with and without SecurityManager
 * @run main ResourceManagerWitupTest
 */

import java.net.SocketException;

public class ResourceManagerWitupTest {

    public static void main(String[] args) throws Exception {
        testWithoutSecurityManager();
        testWithSecurityManagerLimit();
    }

    private static void testWithoutSecurityManager() throws Exception {
        // Remove any SecurityManager
        System.setSecurityManager(null);

        // Calling beforeUdpCreate should not throw even if called many times
        for (int i = 0; i < 100; i++) {
            ResourceManager.beforeUdpCreate();
        }
    }

    private static void testWithSecurityManagerLimit() throws Exception {
        // Install a dummy SecurityManager to activate the limit logic
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow everything
            }
        };
        System.setSecurityManager(sm);

        // The maxSockets is private static final, but we know default is 25 or from property
        // We will call beforeUdpCreate maxSockets times successfully, then one more to get exception

        // We do not have direct access to maxSockets, so we try to call until exception
        int successCount = 0;
        try {
            while (true) {
                ResourceManager.beforeUdpCreate();
                successCount++;
            }
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (!"maximum number of DatagramSockets reached".equals(msg)) {
                throw new RuntimeException("Unexpected SocketException message: " + msg);
            }
        }

        if (successCount == 0) {
            throw new RuntimeException("beforeUdpCreate threw SocketException immediately, expected some successful calls");
        }

        // After exception, calling afterUdpClose should allow one more beforeUdpCreate
        ResourceManager.afterUdpClose();

        // Now one more beforeUdpCreate should succeed
        ResourceManager.beforeUdpCreate();

        // Clean up: remove SecurityManager
        System.setSecurityManager(null);
    }
}
