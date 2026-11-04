/*
 * @test
 * @summary WITUP-generated checks for HttpURLConnection.addRequestProperty null key handling and basic acceptance
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);
        conn.addRequestProperty("X-Witup", "value");

        HttpURLConnection conn2 = new HttpURLConnection(url, Proxy.NO_PROXY);
        try {
            conn2.addRequestProperty(null, "value");
            throw new AssertionError("NullPointerException expected for null key");
        } catch (NullPointerException e) {
            if (!"key is null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
