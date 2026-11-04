/* @test    @summary Tests for java.net.ServerSocket constructor with port parameter    @run main ServerSocketWitupTest */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testValidPort();
        testNegativePort();
        testPortExceedsMax();
        testZeroPort();
    }

    private static void testValidPort() {
        try {
            ServerSocket ss = new ServerSocket(0);
            if (ss != null && ss.getLocalPort() >= 0) {
                System.out.println("testValidPort passed");
                ss.close();
            } else {
                System.out.println("testValidPort failed: invalid local port");
            }
        } catch (IOException e) {
            System.out.println("testValidPort failed with IOException: " + e.getMessage());
        }
    }

    private static void testNegativePort() {
        try {
            new ServerSocket(-1);
            System.out.println("testNegativePort failed: expected exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testNegativePort passed");
        } catch (IOException e) {
            System.out.println("testNegativePort failed with IOException: " + e.getMessage());
        }
    }

    private static void testPortExceedsMax() {
        try {
            new ServerSocket(70000);
            System.out.println("testPortExceedsMax failed: expected exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testPortExceedsMax passed");
        } catch (IOException e) {
            System.out.println("testPortExceedsMax failed with IOException: " + e.getMessage());
        }
    }

    private static void testZeroPort() {
        try {
            ServerSocket ss = new ServerSocket(0);
            if (ss != null && ss.getLocalPort() >= 0) {
                System.out.println("testZeroPort passed");
                ss.close();
            } else {
                System.out.println("testZeroPort failed: invalid local port");
            }
        } catch (IOException e) {
            System.out.println("testZeroPort failed with IOException: " + e.getMessage());
        }
    }
}
