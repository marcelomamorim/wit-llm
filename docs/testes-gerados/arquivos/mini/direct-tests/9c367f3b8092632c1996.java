/*
 * @test
 * @summary Test sun.net.www.URLConnection.getRequestProperty(String) behavior
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to instantiate abstract class
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() {
                connected = true;
            }
        };

        // Initially connected is false, getRequestProperty should return null
        if (conn.getRequestProperty("anyKey") != null) {
            throw new RuntimeException("Expected null when not connected");
        }

        // Set connected to true and verify IllegalStateException is thrown
        conn.connect();
        try {
            conn.getRequestProperty("anyKey");
            throw new RuntimeException("Expected IllegalStateException when connected");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
