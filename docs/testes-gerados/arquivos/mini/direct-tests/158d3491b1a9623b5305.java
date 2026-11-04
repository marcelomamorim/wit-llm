/*
 * @test
 * @summary Test java.net.ServerSocket.setSocketFactory behavior
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketImplFactory;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetFactoryOnce();
        testSetFactorySecurityCheck();
    }

    private static void testSetFactoryOnce() throws Exception {
        // Reset factory via reflection for test isolation
        java.lang.reflect.Field factoryField = ServerSocket.class.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(null, null);

        SocketImplFactory fac1 = new SocketImplFactory() {
            public java.net.SocketImpl createSocketImpl() {
                return null;
            }
        };

        ServerSocket.setSocketFactory(fac1);

        try {
            ServerSocket.setSocketFactory(fac1);
            throw new RuntimeException("Expected SocketException not thrown");
        } catch (SocketException e) {
            // expected
            if (!"factory already defined".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetFactorySecurityCheck() throws Exception {
        // Reset factory via reflection for test isolation
        java.lang.reflect.Field factoryField = ServerSocket.class.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(null, null);

        // Install a SecurityManager that denies setFactory
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkSetFactory() {
                throw new SecurityException("setFactory not allowed");
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow everything else
            }
        });

        SocketImplFactory fac = new SocketImplFactory() {
            public java.net.SocketImpl createSocketImpl() {
                return null;
            }
        };

        try {
            ServerSocket.setSocketFactory(fac);
            throw new RuntimeException("Expected SecurityException not thrown");
        } catch (SecurityException e) {
            if (!"setFactory not allowed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected SecurityException message: " + e.getMessage());
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
