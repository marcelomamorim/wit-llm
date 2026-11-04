/*
 * @test
 * @summary Verifies that HttpsURLConnectionImpl.setUseCaches delegates the value observed through the public getUseCaches API
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/witup");
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected HttpsURLConnection but got " + conn.getClass());
        }
        HttpsURLConnection https = (HttpsURLConnection) conn;

        https.setUseCaches(false);
        if (https.getUseCaches()) {
            throw new RuntimeException("getUseCaches() should be false after setUseCaches(false)");
        }

        https.setUseCaches(true);
        if (!https.getUseCaches()) {
            throw new RuntimeException("getUseCaches() should be true after setUseCaches(true)");
        }

        https.setUseCaches(false);
        https.setUseCaches(false);
        if (https.getUseCaches()) {
            throw new RuntimeException("Repeated setUseCaches(false) should keep caching disabled");
        }
    }
}
