/*
 * @test
 * @summary Verifies that setFixedLengthStreamingMode on an HttpsURLConnectionImpl obtained via URL.openConnection() accepts valid values and keeps the connection usable
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/");
        URLConnection uc = url.openConnection();

        if (!(uc instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected an HttpsURLConnection but got " + uc.getClass().getName());
        }
        if (!"sun.net.www.protocol.https.HttpsURLConnectionImpl".equals(uc.getClass().getName())) {
            throw new RuntimeException("Unexpected implementation class: " + uc.getClass().getName());
        }

        HttpsURLConnection conn = (HttpsURLConnection) uc;

        conn.setFixedLengthStreamingMode(0);
        conn.setFixedLengthStreamingMode(1024);
        conn.setFixedLengthStreamingMode(Integer.MAX_VALUE);
        conn.setFixedLengthStreamingMode(5);

        if (conn.getURL().getPort() != -1) {
            throw new RuntimeException("Unexpected port: " + conn.getURL().getPort());
        }
        if (!"https".equals(conn.getURL().getProtocol())) {
            throw new RuntimeException("Unexpected protocol: " + conn.getURL().getProtocol());
        }
    }
}
