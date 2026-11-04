/* @test    @summary Test for ResourceManager.beforeUdpCreate() exception path    @run main ResourceManagerWitupTest */

import java.net.SocketException;
import sun.net.ResourceManager;

public class ResourceManagerWitupTest {

    public static void main(String[] args) {
        testBeforeUdpCreateThrows();
        testBeforeUdpCreateNoException();
    }

    private static void testBeforeUdpCreateThrows() {
        try {
            // Simulate security manager present
            System.setSecurityManager(new SecurityManager());
            // Force the condition to throw exception
            // Save current maxSockets
            int originalMax = getMaxSockets();
            // Set maxSockets to 0 to trigger exception
            setMaxSockets(0);
            try {
                ResourceManager.beforeUdpCreate();
                System.out.println("FAIL: Expected SocketException was not thrown");
            } catch (SocketException e) {
                System.out.println("PASS: SocketException thrown as expected");
            } finally {
                setMaxSockets(originalMax);
                System.setSecurityManager(null);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testBeforeUdpCreateNoException() {
        try {
            // Ensure no security manager
            System.setSecurityManager(null);
            // Call method, should not throw
            ResourceManager.beforeUdpCreate();
            System.out.println("PASS: No exception as expected when no security manager");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    // Helper methods to manipulate maxSockets for testing
    private static int getMaxSockets() {
        try {
            java.lang.reflect.Field field = ResourceManager.class.getDeclaredField("maxSockets");
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void setMaxSockets(int value) {
        try {
            java.lang.reflect.Field field = ResourceManager.class.getDeclaredField("maxSockets");
            field.setAccessible(true);
            field.setInt(null, value);
        } catch (Exception e) {
            // ignore
        }
    }
}
