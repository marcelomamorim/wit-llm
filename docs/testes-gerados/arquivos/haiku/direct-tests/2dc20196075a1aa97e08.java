/*
 * @test
 * @summary Verifies setConnectTimeout rejects negative values and accepts non-negative values
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    static void checkIllegalArgument(int timeout) throws Exception {
        URL url = new URL("http://localhost/");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);
        boolean thrown = false;
        try {
            conn.setConnectTimeout(timeout);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for timeout " + timeout);
        }
    }

    static void checkAccepted(int timeout) throws Exception {
        URL url = new URL("http://localhost/");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);
        conn.setConnectTimeout(timeout);
    }

    public static void main(String[] args) throws Exception {
        checkIllegalArgument(-1);
        checkIllegalArgument(Integer.MIN_VALUE);
        checkAccepted(0);
        checkAccepted(1);
        checkAccepted(5000);
        checkAccepted(Integer.MAX_VALUE);
    }
}
