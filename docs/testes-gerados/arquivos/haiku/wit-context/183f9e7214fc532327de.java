/*
 * @test
 * @summary WITUP-generated check that setIfModifiedSince on an unconnected HTTPS connection completes without exception
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
            throw new RuntimeException("Expected an HttpsURLConnection instance");
        }
        conn.setIfModifiedSince(0L);
        conn.setIfModifiedSince(1000000L);
        conn.setIfModifiedSince(Long.MAX_VALUE);
    }
}
