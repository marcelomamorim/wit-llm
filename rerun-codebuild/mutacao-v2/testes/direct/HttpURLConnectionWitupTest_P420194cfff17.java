/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.setReadTimeout(int) method
 * @run main HttpURLConnectionWitupTest_P420194cfff17
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.InetSocketAddress;

public class HttpURLConnectionWitupTest_P420194cfff17 {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL
        URL url = new URL("http://example.com");
        // Use public constructor with URL and Proxy
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("localhost", 8080));
        HttpURLConnection conn = new HttpURLConnection(url, proxy) {
            public void connect() {}
            public void disconnect() {}
            public boolean usingProxy() { return false; }
        };

        // Test setting a valid positive timeout
        conn.setReadTimeout(1000);
        if (conn.getReadTimeout() != 1000) {
            throw new RuntimeException("Failed to set positive readTimeout");
        }

        // Test setting zero timeout
        conn.setReadTimeout(0);
        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("Failed to set zero readTimeout");
        }

        // Test setting negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }

        System.out.println("HttpURLConnection.setReadTimeout(int) tests passed.");
    }
}
