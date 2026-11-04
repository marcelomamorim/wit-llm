/*
 * @test
 * @summary Verifies that setAllowUserInteraction on HttpsURLConnectionImpl delegates and is observable through the public getter
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/");
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpsURLConnection)) {
            throw new RuntimeException("Unexpected connection type: " + conn.getClass());
        }
        HttpsURLConnection https = (HttpsURLConnection) conn;

        https.setAllowUserInteraction(true);
        if (!https.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be true after setting true");
        }

        https.setAllowUserInteraction(false);
        if (https.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be false after setting false");
        }

        https.setAllowUserInteraction(true);
        https.setAllowUserInteraction(true);
        if (!https.getAllowUserInteraction()) {
            throw new RuntimeException("Repeated set to true not observed");
        }
    }
}
