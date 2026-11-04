/*
 * @test
 * @summary Verifies that setIfModifiedSince on an HTTPS connection is observable through getIfModifiedSince
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/witup");
        URLConnection c = url.openConnection();
        if (!(c instanceof HttpsURLConnection)) {
            throw new RuntimeException("Expected HttpsURLConnection but got " + c.getClass().getName());
        }
        HttpsURLConnection conn = (HttpsURLConnection) c;

        long[] values = {0L, 1000L, 1234567890123L};
        for (long v : values) {
            conn.setIfModifiedSince(v);
            long actual = conn.getIfModifiedSince();
            if (actual != v) {
                throw new RuntimeException("Expected " + v + " but got " + actual);
            }
        }

        conn.setIfModifiedSince(5000L);
        conn.setIfModifiedSince(7000L);
        if (conn.getIfModifiedSince() != 7000L) {
            throw new RuntimeException("Last set value should win, got " + conn.getIfModifiedSince());
        }
    }
}
