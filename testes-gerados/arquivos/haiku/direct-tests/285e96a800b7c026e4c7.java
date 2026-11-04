/*
 * @test
 * @summary Verify that FtpClient.allocate sends the ALLO command and returns the same client instance
 * @modules java.base/sun.net.ftp
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

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        try (ServerSocket ss = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            final String[] received = new String[1];
            final Throwable[] failure = new Throwable[1];

            Thread server = new Thread(() -> {
                try (Socket s = ss.accept()) {
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(s.getInputStream(), "ISO8859_1"));
                    Writer w = new OutputStreamWriter(s.getOutputStream(), "ISO8859_1");
                    w.write("220 Ready\r\n");
                    w.flush();
                    received[0] = r.readLine();
                    w.write("200 OK\r\n");
                    w.flush();
                } catch (Throwable t) {
                    failure[0] = t;
                }
            });
            server.start();

            FtpClient client = FtpClient.create();
            client.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), ss.getLocalPort()));

            FtpClient result = client.allocate(100L);

            server.join(10000);

            if (failure[0] != null) {
                throw new RuntimeException("Fake server failed", failure[0]);
            }
            if (!"ALLO 100".equals(received[0])) {
                throw new RuntimeException("Unexpected command: " + received[0]);
            }
            if (result != client) {
                throw new RuntimeException("allocate must return this client");
            }
        }
    }
}
