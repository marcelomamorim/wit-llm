/* @test    @summary Test setReceiveBufferSize method of ServerSocket for valid and invalid inputs    @run main ServerSocketWitupTest */

import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeNegative();
        testSetReceiveBufferSizeWhenClosed();
    }

    private static void testSetReceiveBufferSizeValid() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            int validSize = 1024;
            serverSocket.setReceiveBufferSize(validSize);
            System.out.println("testSetReceiveBufferSizeValid passed");
            serverSocket.close();
        } catch (Exception e) {
            System.err.println("testSetReceiveBufferSizeValid failed: " + e);
        }
    }

    private static void testSetReceiveBufferSizeNegative() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            int invalidSize = -1;
            serverSocket.setReceiveBufferSize(invalidSize);
            System.err.println("testSetReceiveBufferSizeNegative failed: no exception thrown");
            serverSocket.close();
        } catch (IllegalArgumentException e) {
            System.out.println("testSetReceiveBufferSizeNegative passed");
        } catch (Exception e) {
            System.err.println("testSetReceiveBufferSizeNegative failed: " + e);
        }
    }

    private static void testSetReceiveBufferSizeWhenClosed() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.close();
            int size = 1024;
            serverSocket.setReceiveBufferSize(size);
            System.err.println("testSetReceiveBufferSizeWhenClosed failed: no exception thrown");
        } catch (SocketException e) {
            System.out.println("testSetReceiveBufferSizeWhenClosed passed");
        } catch (Exception e) {
            System.err.println("testSetReceiveBufferSizeWhenClosed failed: " + e);
        }
    }
}
