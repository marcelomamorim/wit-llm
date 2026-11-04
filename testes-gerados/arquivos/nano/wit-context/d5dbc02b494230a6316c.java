/*
 * @test
 * @summary Test for sun.net.NetworkServer.startServer(int)
 * @run main NetworkServerWitupTest
 */

import java.io.*;
import java.net.*;

public class NetworkServerWitupTest {

    public static void main(String[] args) throws Exception {
        testValidPort();
        testNegativePort();
        testZeroPort();
        testPortExceedingMax();
    }

    public static void testValidPort() {
        try {
            sun.net.NetworkServer server = new sun.net.NetworkServer();
            server.startServer(8080);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid port", e);
        }
    }

    public static void testNegativePort() {
        try {
            sun.net.NetworkServer server = new sun.net.NetworkServer();
            server.startServer(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative port");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for negative port", e);
        }
    }

    public static void testZeroPort() {
        try {
            sun.net.NetworkServer server = new sun.net.NetworkServer();
            server.startServer(0);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for zero port", e);
        }
    }

    public static void testPortExceedingMax() {
        try {
            sun.net.NetworkServer server = new sun.net.NetworkServer();
            server.startServer(70000);
            throw new AssertionError("Expected IllegalArgumentException for port > 65535");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for port > 65535", e);
        }
    }
}
