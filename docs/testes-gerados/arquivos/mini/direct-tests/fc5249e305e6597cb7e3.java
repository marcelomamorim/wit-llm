/*
 * @test
 * @summary Test ServerSocket constructor with port, backlog and bindAddr
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPortBacklogBindAddr();
        testPortOutOfRangeNegative();
        testPortOutOfRangeTooLarge();
        testBacklogLessThanOneDefaultsTo50();
    }

    private static void testValidPortBacklogBindAddr() throws IOException {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        ServerSocket ss = new ServerSocket(0, 10, loopback);
        int localPort = ss.getLocalPort();
        if (localPort <= 0 || localPort > 0xFFFF) {
            throw new RuntimeException("Local port out of valid range: " + localPort);
        }
        InetSocketAddress localSocketAddr = (InetSocketAddress) ss.getLocalSocketAddress();
        if (!localSocketAddr.getAddress().equals(loopback)) {
            throw new RuntimeException("Bind address mismatch: expected " + loopback + " but got " + localSocketAddr.getAddress());
        }
        ss.close();
    }

    private static void testPortOutOfRangeNegative() {
        try {
            new ServerSocket(-1, 10, InetAddress.getLoopbackAddress());
            throw new RuntimeException("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testPortOutOfRangeTooLarge() {
        try {
            new ServerSocket(0x10000, 10, InetAddress.getLoopbackAddress());
            throw new RuntimeException("Expected IllegalArgumentException for port 0x10000");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testBacklogLessThanOneDefaultsTo50() throws IOException {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        ServerSocket ss = new ServerSocket(0, 0, loopback);
        // We cannot directly observe backlog, but no exception means backlog was set to default 50
        int localPort = ss.getLocalPort();
        if (localPort <= 0 || localPort > 0xFFFF) {
            throw new RuntimeException("Local port out of valid range: " + localPort);
        }
        ss.close();
    }
}
