/*
 * @test
 * @summary Verifies that HttpURLConnection.write(byte[]) fails with NullPointerException for a null array
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        HttpURLConnection c = new HttpURLConnection(url, "localhost", 80);
        boolean thrown = false;
        try {
            c.write(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for write(null)");
        }
    }
}
