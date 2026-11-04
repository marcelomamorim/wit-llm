/*
 * @test
 * @summary structureMount sends SMNT with the given structure and returns the same client
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
            ss.setSoTimeout(10000);
            final String[] received = new String[1];
            final Throwable[] failure = new Throwable[1];

            Thread server = new Thread(() -> {
                try (Socket s = ss.accept()) {
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(s.getInputStream(), "ISO-8859-1"));
                    Writer w = new OutputStreamWriter(s.getOutputStream(), "ISO-8859-1");
                    w.write("220 Ready\r\n");
                    w.flush();
                    received[0] = r.readLine();
                    w.write("200 OK\r\n");
                    w.flush();
                    while (r.readLine() != null) {
                        // drain until client closes
                    }
                } catch (Throwable e) {
                    failure[0] = e;
                }
            });
            server.start();

            FtpClient client = FtpClient.create();
            try {
                client.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(),
                        ss.getLocalPort()));
                FtpClient result = client.structureMount("F");
                if (result != client) {
                    throw new RuntimeException("structureMount must return this client");
                }
            } finally {
                client.close();
            }

            server.join();
            if (failure[0] != null) {
                throw new RuntimeException("server side failed", failure[0]);
            }
            if (!"SMNT F".equals(received[0])) {
                throw new RuntimeException("unexpected command: " + received[0]);
            }
        }
    }
}
