/*
 * @test
 * @summary Verifies that HttpURLConnection.write(byte[]) fails with NullPointerException for a null array
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Proxy;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/witup");
        sun.net.www.protocol.http.HttpURLConnection conn =
                new sun.net.www.protocol.http.HttpURLConnection(url, Proxy.NO_PROXY);
        boolean thrown = false;
        try {
            conn.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for write((byte[]) null)");
        }
    }
}
