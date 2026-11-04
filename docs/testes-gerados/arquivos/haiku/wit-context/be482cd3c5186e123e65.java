/*
 * @test
 * @summary Verifies the public allowUserInteraction accessor behavior of HttpsURLConnectionImpl obtained via the public URL API
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost:1/");
        URLConnection c = url.openConnection();
        if (!(c instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected HttpsURLConnection but got " + c.getClass().getName());
        }
        HttpsURLConnection h = (HttpsURLConnection) c;

        if (h.getAllowUserInteraction()) {
            throw new RuntimeException("Default allowUserInteraction should be false");
        }

        h.setAllowUserInteraction(true);
        if (!h.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be true after setting true");
        }

        h.setAllowUserInteraction(false);
        if (h.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be false after setting false");
        }
    }
}
