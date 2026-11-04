/*
 * @test
 * @summary Checks that setDoInput on an HTTPS connection obtained through the public URL API is reflected by getDoInput before connecting
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/witup");
        URLConnection uc = url.openConnection();
        check(uc instanceof HttpsURLConnection, "openConnection did not return an HttpsURLConnection");
        HttpsURLConnection conn = (HttpsURLConnection) uc;

        conn.setDoInput(false);
        check(!conn.getDoInput(), "getDoInput should be false after setDoInput(false)");

        conn.setDoInput(true);
        check(conn.getDoInput(), "getDoInput should be true after setDoInput(true)");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
