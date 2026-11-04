/*
 * @test
 * @summary Verifies that FtpClient.rename sends RNFR and RNTO in order and returns the same client
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        final ServerSocket ss = new ServerSocket(0, 1, InetAddress.getLoopbackAddress());
        final List<String> received = new ArrayList<>();
        Thread server = new Thread(() -> {
            try (Socket s = ss.accept()) {
                OutputStream os = s.getOutputStream();
                os.write("220 ready\r\n".getBytes("ISO8859_1"));
                os.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), "ISO8859_1"));
                String line;
                while ((line = br.readLine()) != null) {
                    synchronized (received) {
                        received.add(line);
                    }
                    if (line.startsWith("QUIT")) {
                        os.write("221 bye\r\n".getBytes("ISO8859_1"));
                        os.flush();
                        break;
                    }
                    os.write("250 ok\r\n".getBytes("ISO8859_1"));
                    os.flush();
                }
            } catch (Exception e) {
                // reported through the received-list assertions below
            }
        });
        server.start();

        sun.net.ftp.FtpClient client = sun.net.ftp.FtpClient.create();
        try {
            client.connect(new java.net.InetSocketAddress(InetAddress.getLoopbackAddress(), ss.getLocalPort()));
            sun.net.ftp.FtpClient result = client.rename("old.txt", "new.txt");

            if (result != client) {
                throw new RuntimeException("rename must return this client");
            }
        } finally {
            try {
                client.close();
            } catch (Exception ignore) {
            }
            ss.close();
        }

        server.join(10000);

        synchronized (received) {
            if (received.size() < 2) {
                throw new RuntimeException("expected RNFR and RNTO, got: " + received);
            }
            if (!received.get(0).equals("RNFR old.txt")) {
                throw new RuntimeException("unexpected first command: " + received.get(0));
            }
            if (!received.get(1).equals("RNTO new.txt")) {
                throw new RuntimeException("unexpected second command: " + received.get(1));
            }
        }
    }
}
