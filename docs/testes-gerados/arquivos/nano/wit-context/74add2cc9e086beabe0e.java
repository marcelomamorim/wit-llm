/* @test    @summary Test for ServerSocket(int port, int backlog) constructor with valid and invalid inputs    @run main ServerSocketWitupTest */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNegativePort();
        testPortExceedsMax();
        testPortBelowZero();
    }

    private static void testValidConstructor() {
        try {
            ServerSocket ss = new ServerSocket(0, 50);
            if (ss != null && ss.getLocalPort() >= 0) {
                System.out.println("PASS: Valid constructor with port 0 and backlog 50");
                ss.close();
            } else {
                System.out.println("FAIL: Valid constructor returned invalid socket");
            }
        } catch (IOException e) {
            System.out.println("FAIL: Valid constructor threw IOException: " + e);
        }
    }

    private static void testNegativePort() {
        try {
            new ServerSocket(-1, 50);
            System.out.println("FAIL: Negative port did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Negative port threw IllegalArgumentException");
        } catch (IOException e) {
            System.out.println("FAIL: Negative port threw IOException instead of IllegalArgumentException: " + e);
        }
    }

    private static void testPortExceedsMax() {
        try {
            new ServerSocket(70000, 50);
            System.out.println("FAIL: Port > 65535 did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Port > 65535 threw IllegalArgumentException");
        } catch (IOException e) {
            System.out.println("FAIL: Port > 65535 threw IOException instead of IllegalArgumentException: " + e);
        }
    }

    private static void testPortBelowZero() {
        try {
            new ServerSocket(-5, 50);
            System.out.println("FAIL: Port < 0 did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Port < 0 threw IllegalArgumentException");
        } catch (IOException e) {
            System.out.println("FAIL: Port < 0 threw IOException instead of IllegalArgumentException: " + e);
        }
    }
}
