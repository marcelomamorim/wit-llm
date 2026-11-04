/* @test    @summary Test constructor ServerSocket(int port, int backlog) for basic behavior and exception handling    @run main ServerSocketWitupTest */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testValidBacklog();
        testInvalidBacklogNegative();
        testInvalidBacklogTooLarge();
    }

    static void testValidBacklog() {
        try {
            ServerSocket ss = new ServerSocket(0, 50);
            if (ss != null && ss.getLocalPort() >= 0) {
                System.out.println("PASS: Valid backlog creates ServerSocket with local port " + ss.getLocalPort());
                ss.close();
            } else {
                System.out.println("FAIL: Valid backlog test failed");
            }
        } catch (IOException e) {
            System.out.println("FAIL: Exception thrown during valid backlog test: " + e);
        }
    }

    static void testInvalidBacklogNegative() {
        try {
            new ServerSocket(0, -1);
            System.out.println("FAIL: Negative backlog did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Negative backlog threw IllegalArgumentException as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException for negative backlog: " + e);
        }
    }

    static void testInvalidBacklogTooLarge() {
        int tooLargeBacklog = Integer.MAX_VALUE;
        try {
            new ServerSocket(0, tooLargeBacklog);
            System.out.println("PASS: Very large backlog accepted (behavior depends on implementation)");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Very large backlog threw IllegalArgumentException as expected");
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown for large backlog: " + e);
        }
    }
}
