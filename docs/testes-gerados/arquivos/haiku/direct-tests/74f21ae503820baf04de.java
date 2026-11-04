/*
 * @test
 * @summary Verifies addRequestProperty argument handling on a fresh sun HttpURLConnection
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Proxy;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullKeyThrowsNullPointerException();
        testValidHeaderAccepted();
        testContentTypeHeaderAccepted();
    }

    static sun.net.www.protocol.http.HttpURLConnection newConnection() throws Exception {
        URL url = new URL("http://localhost:8080/witup");
        return new sun.net.www.protocol.http.HttpURLConnection(url, Proxy.NO_PROXY);
    }

    static void testNullKeyThrowsNullPointerException() throws Exception {
        sun.net.www.protocol.http.HttpURLConnection c = newConnection();
        boolean thrown = false;
        try {
            c.addRequestProperty(null, "value");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }

    static void testValidHeaderAccepted() throws Exception {
        sun.net.www.protocol.http.HttpURLConnection c = newConnection();
        c.addRequestProperty("X-Witup-Test", "alpha");
        c.addRequestProperty("X-Witup-Test", "beta");
    }

    static void testContentTypeHeaderAccepted() throws Exception {
        sun.net.www.protocol.http.HttpURLConnection c = newConnection();
        c.addRequestProperty("content-type", "text/plain");
        c.addRequestProperty("Content-Type", "text/html");
    }
}
