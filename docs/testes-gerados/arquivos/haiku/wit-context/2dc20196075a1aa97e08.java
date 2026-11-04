/*
 * @test
 * @summary Verify setConnectTimeout rejects negative timeouts and keeps the previous value unchanged
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        HttpURLConnection c = new HttpURLConnection(url, Proxy.NO_PROXY);

        if (c.getConnectTimeout() != 0) {
            throw new RuntimeException("Unexpected default connect timeout: " + c.getConnectTimeout());
        }

        c.setConnectTimeout(0);
        if (c.getConnectTimeout() != 0) {
            throw new RuntimeException("Zero timeout not accepted");
        }

        c.setConnectTimeout(5000);
        if (c.getConnectTimeout() != 5000) {
            throw new RuntimeException("Positive timeout not stored");
        }

        expectIllegalArgument(c, -1);
        expectIllegalArgument(c, Integer.MIN_VALUE);

        if (c.getConnectTimeout() != 5000) {
            throw new RuntimeException("Timeout changed after rejected negative value: " + c.getConnectTimeout());
        }
    }

    private static void expectIllegalArgument(HttpURLConnection c, int timeout) {
        try {
            c.setConnectTimeout(timeout);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for timeout " + timeout);
    }
}
