/* @test    @summary Test setSocketFactory method of ServerSocket    @run main ServerSocketWitupTest */

import java.net.ServerSocket;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketWitupTest {

    public static class DummyFactory implements java.net.SocketImplFactory {
        @Override
        public void createSocketImpl() {
            // Dummy implementation
        }
    }

    public static void main(String[] args) {
        testSetSocketFactoryNull();
        testSetSocketFactoryAlreadySet();
    }

    private static void testSetSocketFactoryNull() {
        try {
            ServerSocket.setSocketFactory(null);
            System.out.println("setSocketFactory with null succeeded");
        } catch (IOException e) {
            System.err.println("Unexpected IOException in testSetSocketFactoryNull: " + e);
        } catch (SocketException e) {
            System.err.println("Unexpected SocketException in testSetSocketFactoryNull: " + e);
        }
    }

    private static void testSetSocketFactoryAlreadySet() {
        try {
            ServerSocket.setSocketFactory(new DummyFactory());
            // Attempt to set again, should throw SocketException
            ServerSocket.setSocketFactory(new DummyFactory());
            System.err.println("Error: Expected SocketException not thrown");
        } catch (SocketException e) {
            if ("factory already defined".equals(e.getMessage())) {
                System.out.println("Correctly caught SocketException for already set factory");
            } else {
                System.err.println("Unexpected message in SocketException: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Unexpected IOException in testSetSocketFactoryAlreadySet: " + e);
        }
    }
}
