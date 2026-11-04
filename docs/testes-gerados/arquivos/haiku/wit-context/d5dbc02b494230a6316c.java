/*
 * @test
 * @summary Verify NetworkServer.startServer rejects out-of-range ports without opening a client connection
 * @modules java.base/sun.net
 * @run main NetworkServerWitupTest
 */

import sun.net.NetworkServer;
import java.io.IOException;

public class NetworkServerWitupTest {
    public static void main(String[] args) throws Exception {
        checkInvalidPort(-1);
        checkInvalidPort(65536);
        checkInvalidPort(Integer.MIN_VALUE);
        checkInvalidPort(Integer.MAX_VALUE);
    }

    static void checkInvalidPort(int port) {
        NetworkServer server = new NetworkServer();
        try {
            server.startServer(port);
            throw new RuntimeException("Expected IllegalArgumentException for port " + port);
        } catch (IllegalArgumentException expected) {
            // expected behavior
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for port " + port, e);
        }
        if (server.clientIsOpen()) {
            throw new RuntimeException("Client connection reported open after failed startServer, port " + port);
        }
    }
}
