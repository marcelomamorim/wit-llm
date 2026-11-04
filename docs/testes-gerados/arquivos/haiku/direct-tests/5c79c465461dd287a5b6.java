/*
 * @test
 * @summary Verifies ResourceManager.beforeUdpCreate behavior with and without a SecurityManager
 * @modules java.base/sun.net
 * @run main ResourceManagerWitupTest
 */

import java.net.SocketException;
import sun.net.ResourceManager;

public class ResourceManagerWitupTest {

    private static final int DEFAULT_LIMIT = 25;

    public static void main(String[] args) throws Exception {
        testNoSecurityManagerNeverThrows();
        testLimitWithSecurityManager();
        System.out.println("PASSED");
    }

    static void testNoSecurityManagerNeverThrows() throws Exception {
        if (System.getSecurityManager() != null) {
            throw new RuntimeException("SecurityManager unexpectedly installed");
        }
        for (int i = 0; i < DEFAULT_LIMIT * 2; i++) {
            ResourceManager.beforeUdpCreate();
        }
    }

    static void testLimitWithSecurityManager() throws Exception {
        System.setSecurityManager(new SecurityManager());
        int created = 0;
        try {
            for (int i = 0; i < DEFAULT_LIMIT; i++) {
                ResourceManager.beforeUdpCreate();
                created++;
            }
            try {
                ResourceManager.beforeUdpCreate();
                throw new RuntimeException("Expected SocketException was not thrown");
            } catch (SocketException e) {
                if (!"maximum number of DatagramSockets reached".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage());
                }
            }
        } finally {
            for (int i = 0; i < created; i++) {
                ResourceManager.afterUdpClose();
            }
        }
        // After releasing all counted sockets, creation must be allowed again.
        ResourceManager.beforeUdpCreate();
        ResourceManager.afterUdpClose();
    }
}
