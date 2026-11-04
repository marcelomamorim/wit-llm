/*
 * @test
 * @summary Verifies setReadTimeout rejects negative values and stores non-negative values
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Proxy;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static HttpURLConnection newConnection() throws Exception {
        URL url = new URL("http://localhost/");
        return new HttpURLConnection(url, Proxy.NO_PROXY);
    }

    static void testZeroAccepted() throws Exception {
        HttpURLConnection c = newConnection();
        c.setReadTimeout(0);
        check(c.getReadTimeout() == 0, "expected read timeout 0");
    }

    static void testPositiveStored() throws Exception {
        HttpURLConnection c = newConnection();
        c.setReadTimeout(5000);
        check(c.getReadTimeout() == 5000, "expected read timeout 5000");
    }

    static void testNegativeRejected() throws Exception {
        HttpURLConnection c = newConnection();
        c.setReadTimeout(1234);
        boolean thrown = false;
        try {
            c.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for -1");
        check(c.getReadTimeout() == 1234, "value must be unchanged after rejected negative timeout");
    }

    static void testMinValueRejected() throws Exception {
        HttpURLConnection c = newConnection();
        boolean thrown = false;
        try {
            c.setReadTimeout(Integer.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for Integer.MIN_VALUE");
    }

    public static void main(String[] args) throws Exception {
        testZeroAccepted();
        testPositiveStored();
        testNegativeRejected();
        testMinValueRejected();
    }
}
