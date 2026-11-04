/*
 * @test
 * @summary Verifies NetworkServer.startServer(int) binds a port, dispatches accepted connections to serviceRequest() on a clone with an open client socket, and fails with IOException when the port is already in use.
 * @run main NetworkServerWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NetworkServerWitupTest {

    static class RecordingServer extends NetworkServer {
        final CountDownLatch served = new CountDownLatch(1);
        volatile boolean openDuringService;
        volatile boolean outputNotNull;
        volatile boolean inputNotNull;

        public void serviceRequest() {
            openDuringService = clientIsOpen();
            outputNotNull = clientOutput != null;
            inputNotNull = clientInput != null;
            served.countDown();
        }
    }

    static int freePort() throws IOException {
        try (ServerSocket ss = new ServerSocket(0)) {
            return ss.getLocalPort();
        }
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testServesConnection() throws Exception {
        int port = freePort();
        RecordingServer server = new RecordingServer();
        server.startServer(port);
        try (Socket client = new Socket("localhost", port)) {
            check(server.served.await(10, TimeUnit.SECONDS),
                  "serviceRequest was not invoked for accepted connection");
        }
        check(server.openDuringService, "clientIsOpen() should be true during service");
        check(server.outputNotNull, "clientOutput should be set during service");
        check(server.inputNotNull, "clientInput should be set during service");
    }

    static void testPortInUseThrowsIOException() throws Exception {
        try (ServerSocket occupied = new ServerSocket(0)) {
            int port = occupied.getLocalPort();
            RecordingServer server = new RecordingServer();
            boolean thrown = false;
            try {
                server.startServer(port);
            } catch (IOException expected) {
                thrown = true;
            }
            check(thrown, "startServer on an occupied port should throw IOException");
            check(!server.clientIsOpen(), "no client should be open after failed start");
        }
    }

    public static void main(String[] args) {
        int code = 0;
        try {
            testServesConnection();
            testPortInUseThrowsIOException();
        } catch (Throwable t) {
            t.printStackTrace();
            code = 1;
        }
        System.exit(code);
    }
}
