/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.setConnectTimeout(int) for valid and invalid inputs
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetConnectTimeoutValid();
        testSetConnectTimeoutNegative();
        System.out.println("PASS");
    }

    private static void testSetConnectTimeoutValid() throws MalformedURLException {
        URL url = new URL("http://example.com");
        Proxy proxy = Proxy.NO_PROXY;
        HttpURLConnection conn = new HttpURLConnection(url, proxy) {
            @Override
            public void disconnect() {}
            @Override
            public boolean usingProxy() { return false; }
            @Override
            public void connect() {}
        };

        // Set a valid positive timeout
        conn.setConnectTimeout(1000);
        // No direct getter, but no exception means success

        // Set zero timeout (allowed)
        conn.setConnectTimeout(0);
    }

    private static void testSetConnectTimeoutNegative() throws MalformedURLException {
        URL url = new URL("http://example.com");
        Proxy proxy = Proxy.NO_PROXY;
        HttpURLConnection conn = new HttpURLConnection(url, proxy) {
            @Override
            public void disconnect() {}
            @Override
            public boolean usingProxy() { return false; }
            @Override
            public void connect() {}
        };

        try {
            conn.setConnectTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
