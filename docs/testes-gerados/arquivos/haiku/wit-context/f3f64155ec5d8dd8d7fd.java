/*
 * @test
 * @summary Verifies that reading from the HTTP input stream after close throws IOException and that a normal read returns the response body
 * @run main HttpURLConnectionWitupTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class HttpURLConnectionWitupTest {

    static final String BODY = "hello";

    public static void main(String[] args) throws Exception {
        try (ServerSocket ss = new ServerSocket(0, 2, InetAddress.getLoopbackAddress())) {
            Thread server = new Thread(() -> {
                for (int i = 0; i < 2; i++) {
                    try (Socket s = ss.accept()) {
                        BufferedReader r = new BufferedReader(
                                new InputStreamReader(s.getInputStream(), "US-ASCII"));
                        String line;
                        while ((line = r.readLine()) != null && !line.isEmpty()) {
                        }
                        OutputStream os = s.getOutputStream();
                        String resp = "HTTP/1.1 200 OK\r\n"
                                + "Content-Type: text/plain\r\n"
                                + "Content-Length: " + BODY.length() + "\r\n"
                                + "Connection: close\r\n\r\n" + BODY;
                        os.write(resp.getBytes("US-ASCII"));
                        os.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            server.start();

            URL url = new URL("http://127.0.0.1:" + ss.getLocalPort() + "/");

            URLConnection c1 = url.openConnection();
            InputStream in1 = c1.getInputStream();
            byte[] buf = new byte[64];
            StringBuilder sb = new StringBuilder();
            int n;
            while ((n = in1.read(buf)) != -1) {
                sb.append(new String(buf, 0, n, "US-ASCII"));
            }
            in1.close();
            if (!BODY.equals(sb.toString())) {
                throw new RuntimeException("Unexpected body: " + sb);
            }

            URLConnection c2 = url.openConnection();
            InputStream in2 = c2.getInputStream();
            in2.close();
            boolean thrown = false;
            try {
                in2.read(new byte[16]);
            } catch (IOException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("read(byte[]) after close did not throw IOException");
            }

            server.join();
        }
    }
}
