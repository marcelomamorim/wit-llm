/*
 * @test
 * @summary Verifies that setUseCaches on an unconnected HttpsURLConnectionImpl stores and reports the requested value
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/");
        URLConnection c = url.openConnection();
        if (!(c instanceof HttpsURLConnection)) {
            throw new RuntimeException("connection is not an HttpsURLConnection");
        }
        HttpsURLConnection conn = (HttpsURLConnection) c;

        conn.setUseCaches(false);
        if (conn.getUseCaches()) {
            throw new RuntimeException("useCaches should be false");
        }

        conn.setUseCaches(true);
        if (!conn.getUseCaches()) {
            throw new RuntimeException("useCaches should be true");
        }

        conn.setUseCaches(false);
        if (conn.getUseCaches()) {
            throw new RuntimeException("useCaches should be false after reset");
        }
    }
}
