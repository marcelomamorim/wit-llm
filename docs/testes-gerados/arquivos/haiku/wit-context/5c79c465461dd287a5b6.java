/*
 * @test
 * @summary Verifies ResourceManager.beforeUdpCreate and afterUdpClose socket limit behavior
 * @modules java.base/sun.net
 * @run main ResourceManagerWitupTest
 */
import java.net.SocketException;
import java.security.Permission;
import sun.net.ResourceManager;

public class ResourceManagerWitupTest {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            ResourceManager.beforeUdpCreate();
        }
        ResourceManager.afterUdpClose();

        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
            }
        });
        try {
            for (int i = 0; i < 25; i++) {
                ResourceManager.beforeUdpCreate();
            }
            boolean thrown = false;
            try {
                ResourceManager.beforeUdpCreate();
            } catch (SocketException e) {
                thrown = true;
                if (!"maximum number of DatagramSockets reached".equals(e.getMessage())) {
                    throw new RuntimeException("unexpected message: " + e.getMessage());
                }
            }
            if (!thrown) {
                throw new RuntimeException("expected SocketException after 25 sockets");
            }
            for (int i = 0; i < 25; i++) {
                ResourceManager.afterUdpClose();
            }
            ResourceManager.beforeUdpCreate();
            ResourceManager.afterUdpClose();
        } finally {
            System.setSecurityManager(null);
        }
    }
}
