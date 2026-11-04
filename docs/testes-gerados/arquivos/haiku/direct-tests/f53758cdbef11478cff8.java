/*
 * @test
 * @summary deleteFile sends DELE and returns the same client on a 2xx reply, and reports a 5xx reply as FtpProtocolException
 * @modules java.base/sun.net.ftp java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FtpClientWitupTest {

    static class FakeServer extends Thread {
        private final ServerSocket ss;
        private final String reply;
        volatile String received;
        volatile Exception error;

        FakeServer(String reply) throws IOException {
            this.ss = new ServerSocket(0, 1, InetAddress.getLoopbackAddress());
            this.reply = reply;
            setDaemon(true);
        }

        int port() {
            return ss.getLocalPort();
        }

        @Override
        public void run() {
            try (Socket s = ss.accept();
                 BufferedReader r = new BufferedReader(
                         new InputStreamReader(s.getInputStream(), "ISO-8859-1"));
                 Writer w = new OutputStreamWriter(s.getOutputStream(), "ISO-8859-1")) {
                w.write("220 Ready\r\n");
                w.flush();
                received = r.readLine();
                w.write(reply + "\r\n");
                w.flush();
            } catch (IOException e) {
                error = e;
            } finally {
                try {
                    ss.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSuccessReturnsSameClient() throws Exception {
        FakeServer server = new FakeServer("250 File deleted");
        server.start();
        FtpClient client = FtpClient.create();
        client.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), server.port()));
        FtpClient ret = client.deleteFile("old.txt");
        check(ret == client, "deleteFile must return the same client instance");
        server.join(10000);
        check(server.error == null, "server error: " + server.error);
        check("DELE old.txt".equals(server.received),
              "expected command 'DELE old.txt' but got: " + server.received);
    }

    static void testErrorReplyThrows() throws Exception {
        FakeServer server = new FakeServer("550 No such file");
        server.start();
        FtpClient client = FtpClient.create();
        client.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), server.port()));
        boolean thrown = false;
        try {
            client.deleteFile("missing.txt");
        } catch (FtpProtocolException e) {
            thrown = true;
        }
        check(thrown, "deleteFile must throw FtpProtocolException on a 550 reply");
        server.join(10000);
        check("DELE missing.txt".equals(server.received),
              "expected command 'DELE missing.txt' but got: " + server.received);
    }

    public static void main(String[] args) throws Exception {
        testSuccessReturnsSameClient();
        testErrorReplyThrows();
    }
}
