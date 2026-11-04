/* @test    @summary Test for ServerSocket setSocketFactory method to verify exception behavior and normal operation    @run main ServerSocketWitupTest */

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketImplFactory;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testSetSocketFactoryWithNull();
        testSetSocketFactoryWhenAlreadySet();
    }

    private static void testSetSocketFactoryWithNull() {
        try {
            // Reset factory to null for test isolation
            // Note: In actual implementation, factory is static and may need reflection or special setup.
            // Here, assuming test environment resets static state or factory is null by default.
            // Since no public setter to reset, this test assumes initial state.
            // Proceed to call with null.
            java.net.ServerSocket.setSocketFactory(null);
            System.out.println("testSetSocketFactoryWithNull: Passed (no exception)");
        } catch (IOException e) {
            System.err.println("testSetSocketFactoryWithNull: Failed with IOException: " + e);
        } catch (SocketException e) {
            System.err.println("testSetSocketFactoryWithNull: Failed with SocketException: " + e);
        } catch (Exception e) {
            System.err.println("testSetSocketFactoryWithNull: Failed with unexpected exception: " + e);
        }
    }

    private static void testSetSocketFactoryWhenAlreadySet() {
        try {
            // Set a dummy factory first
            SocketImplFactory dummyFactory = new SocketImplFactory() {
                public java.net.SocketImpl createSocketImpl() {
                    return null;
                }
            };
            // Set factory for the first time
            java.net.ServerSocket.setSocketFactory(dummyFactory);
            // Attempt to set again, should throw SocketException
            java.net.ServerSocket.setSocketFactory(dummyFactory);
            System.err.println("testSetSocketFactoryWhenAlreadySet: Failed (no exception thrown)");
        } catch (SocketException e) {
            System.out.println("testSetSocketFactoryWhenAlreadySet: Passed (caught expected SocketException)");
        } catch (IOException e) {
            System.err.println("testSetSocketFactoryWhenAlreadySet: Failed with IOException: " + e);
        } catch (Exception e) {
            System.err.println("testSetSocketFactoryWhenAlreadySet: Failed with unexpected exception: " + e);
        }
    }
}
